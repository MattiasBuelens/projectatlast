package projectatlast.query;

import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.*;
import java.util.Map.Entry;

import javax.persistence.Id;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Query {

	@Id long id;
	
	List<Option> options = new ArrayList<Option>();
	Group group;
	
	public Query() { }
	
	public List<Option> getOptions() {
		return Collections.unmodifiableList(options);
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void addOption(Option option) {
		options.add(option);
	}
	
	public void addOptions(Collection<Option> options) {
		this.options.addAll(options);
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	/**
	 * Execute the query and returns the results.
	 *
	 * @return The result set as a grouped map of {@link Activity} objects.
	 */
	public Map<Object, List<Activity>> get() {
		Objectify ofy = Registry.dao().begin();

		// Group options on query kinds
		Map<Class<?>, List<Option>> optionsByKind = groupOptionsByKind();
		Set<Class<?>> kinds = optionsByKind.keySet();

		// Execute the queries
		Map<Class<?>, QueryResultIterable<?>> iterables = execute(ofy, optionsByKind);

		// Get activity keys from all queries
		Map<Class<?>, Set<Key<Activity>>> activityKeys = new HashMap<Class<?>, Set<Key<Activity>>>();
		for(Class<?> kind : kinds) {
			activityKeys.put(kind, getActivityKeys(iterables.get(kind)));
		}

		// Intersect on activity keys
		Set<Key<Activity>> resultKeys = intersect(activityKeys.values());
		// Get activities from datastore
		List<Activity> results = new ArrayList<Activity>(ofy.get(resultKeys).values());
		
		// Process results
		for(Option option : options) {
			results = option.process(results);
		}
		
		// Group results
		Map<Object, List<Activity>> groupedResults;
		if(group == null) {
			groupedResults = new HashMap<Object, List<Activity>>();
			groupedResults.put(null, results);
		} else {
			groupedResults = group.group(results);
		}
		
		return groupedResults;
	}
	
	/**
	 * Group the options on their target entity kind.
	 * 
	 * <p>When an option acts upon a subclass of another option's kind, the other option is
	 * moved to the group of the subclass. This allows both options to act on the same kind.
	 * The resulting map has the minimum amount of groups required to let all options
	 * act on their entity queries.</p>
	 * 
	 * @return Map of <code>Option</code>s grouped by the (sub)class of their kind as a <code>Class</code>.
	 */
	private Map<Class<?>, List<Option>> groupOptionsByKind() {
		Map<Class<?>, List<Option>> optionsByKind = new HashMap<Class<?>, List<Option>>();
		optionsByKind.put(Activity.class, new ArrayList<Option>());

		// Group options in lists by kind
		for(Option option : options) {
			Class<?> optionKind = option.getKind();
			if(!optionsByKind.containsKey(optionKind)) {
				optionsByKind.put(optionKind, new ArrayList<Option>());
			}
			optionsByKind.get(optionKind).add(option);
		}
		
		// Remove superclasses and retain subclasses
		Set<Class<?>> kindsToRemove = new HashSet<Class<?>>();
		for(Class<?> kind : optionsByKind.keySet()) {
			// Skip if already removed
			if(kindsToRemove.contains(kind)) continue;
			for(Class<?> otherKind : optionsByKind.keySet()) {
				// Skip if same kind or already removed
				if(kind.equals(otherKind) || kindsToRemove.contains(otherKind)) continue;
				// If other kind inherits from kind
				if(kind.isAssignableFrom(otherKind)) {
					// Then other kind is the subclass
					List<Option> kindOptions = optionsByKind.get(otherKind);
					kindOptions.addAll(optionsByKind.get(kind));
					kindsToRemove.add(kind);
				// If kind inherits from other kind
				} else if(kind.isAssignableFrom(otherKind)) {
					// Then kind is the subclass
					List<Option> kindOptions = optionsByKind.get(kind);
					kindOptions.addAll(optionsByKind.get(otherKind));
					kindsToRemove.add(otherKind);
				}
			}
		}

		for(Class<?> kind : kindsToRemove) {
			optionsByKind.remove(kind);
		}
		
		return optionsByKind;
	}

	/**
	 * Execute all kind-specific queries with their respective options applied.
	 * 
	 * <p>The queries are created and started asynchronously. The results can
	 * be collected later on when the calling method iterates over the results.</p>
	 * 
	 * @param ofy - <code>Objectify</code> instance to execute the query on.
	 * @param options - query options grouped by kind classes.
	 * @return map of <code>QueryResultIterable</code>s grouped by kind classes.
	 */
	private Map<Class<?>, QueryResultIterable<?>> execute(Objectify ofy, Map<Class<?>, List<Option>> options) {
		// Get kinds to query on
		Set<Class<?>> kinds = options.keySet();

		// Create queries
		Map<Class<?>, com.googlecode.objectify.Query<?>> queries = new HashMap<Class<?>, com.googlecode.objectify.Query<?>>();
		Map<Class<?>, QueryResultIterable<?>> iterables = new HashMap<Class<?>, QueryResultIterable<?>>();
		for(Class<?> kind : kinds) {
			// Create and store query
			com.googlecode.objectify.Query<?> query = ofy.query(kind);
			queries.put(kind, query);
			// Apply options
			for(Option option : options.get(kind)) {
				option.apply(query);
			}
			// Execute query asynchronously
			iterables.put(kind, query.fetch());
		}

		return iterables;
	}
	
	/**
	 * Retrieve the results from a query and maps them onto {@link Activity} keys.
	 * 
	 * <p>The conversion from result entries to keys is handled by
	 * {@link Activity#keyFromObject}.</p>
	 * 
	 * @param iterable - a query result retrieved from an asynchronous query. 
	 * @return set of activity keys.
	 */
	private Set<Key<Activity>> getActivityKeys(QueryResultIterable<?> iterable) {
		// Retrieve the keys and values from this query
		// This is where the asynchronously retrieved results
		// are collected synchronously
		Map<?, ?> result = Registry.dao().keyMap(iterable);

		// Map the results to Activity keys
		Set<Key<Activity>> keys = new HashSet<Key<Activity>>();
		for(Entry<?, ?> entry : result.entrySet()) {
			keys.add(Activity.keyFromObject((Key<?>) entry.getKey(), entry.getValue()));
		}
		return keys;
	}
	
	/**
	 * Determine the intersection of a number of sets,
	 * i.e. only entries which appear in <i>all</i> sets are
	 * retained.
	 * 
	 * @param sets - a collection of sets.
	 * @return the intersection set.
	 */
	private <T> Set<T> intersect(Iterable<Set<T>> sets) {
		Set<T> results = null;
		for(Set<T> set : sets) {
			if(results == null) {
				results = new HashSet<T>(set);
			} else {
				results.retainAll(set);
			}
		}
		return results;
	}

}

package projectatlast.query;

import java.util.*;

import projectatlast.tracking.*;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.*;
import com.googlecode.objectify.Query;

public abstract class SubQuery<T> {
	protected Class<T> kind;
	protected Query<T> query;
	protected Objectify ofy;
	protected List<Option> options;

	@SuppressWarnings("unchecked")
	public SubQuery(Class<?> kind, Objectify ofy) {
		this.kind = (Class<T>)kind;
		this.ofy = ofy;
	}
	
	public Class<T> getKind() {
		return kind;
	}

	public boolean addOption(Option option) {
		if (!option.appliesTo(kind))
			return false;
		options.add(option);
		return true;
	}
	
	public boolean addOptions(Iterable<Option> options) {
		boolean result = true;
		for(Option option : options) {
			result = addOption(option) && result;
		}
		return result;
	}

	/**
	 * Executes the raw sub query.
	 * 
	 * @return The raw results set.
	 */
	public QueryResultIterable<Key<?>> execute() {
		// Prepare the query and run it
		query = prepareQuery();
		QueryResultIterable<?> rawKeys = query.fetchKeys();
		@SuppressWarnings("unchecked")
		QueryResultIterable<Key<?>> keys = (QueryResultIterable<Key<?>>)rawKeys;
		return keys;
	}

	/**
	 * Fetches keys of activity slices belonging to the results from the raw
	 * results set.
	 * 
	 * These slice keys will be compared to the keys from other sub queries to
	 * determine the intersecting key set.
	 * 
	 * @param results
	 *            Raw results from sub query.
	 * @return Set of activity slice keys.
	 */
	public abstract Set<Key<ActivitySlice>> fetchSlices(
			ResultSet results);

	private Query<T> prepareQuery() {
		// Create query
		Query<T> query = ofy.query(kind);
		// Apply options
		for (Option option : options) {
			option.apply(query);
		}
		return query;
	}

	/**
	 * Checks whether this query can be applied to a given class.
	 * 
	 * A query can only be applied to a class if that class is a super class or
	 * the same class as the query's kind. In other words, the query's kind must
	 * be assignable from the given glass.
	 * 
	 * @param cls
	 *            The query kind.
	 * @return
	 */
	public boolean appliesTo(Class<?> cls) {
		return getKind().isAssignableFrom(cls);
	}
}

package projectatlast.query;

import projectatlast.data.Registry;
import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.io.Serializable;
import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class Query implements Serializable {
	private static final long serialVersionUID = 1L;
	private static List<Class<?>> defaultOptionKinds = new ArrayList<Class<?>>();
	private static List<Class<?>> defaultGroupKinds = new ArrayList<Class<?>>();
	static {
		defaultOptionKinds.add(Activity.class);
		defaultOptionKinds.add(ActivitySlice.class);
	}

	List<Option> options = new ArrayList<Option>();
	List<Group> groups = new ArrayList<Group>();

	transient Objectify ofy;

	transient Map<Class<?>, Class<?>> optionKinds;
	transient Map<Class<?>, Class<?>> groupKinds;
	transient Map<Class<?>, List<Option>> optionsByKind;
	transient Map<Class<?>, List<Group>> groupsByKind;

	transient SubQueryFactory subQueryFactory;
	transient Map<Class<?>, SubQuery<?>> subQueries;

	transient ResultSet results;

	public Query() {}

	public List<Option> getOptions() {
		return Collections.unmodifiableList(options);
	}

	public void addOption(Option option) {
		if(this.options == null)
			this.options = new ArrayList<Option>();
		this.options.add(option);
	}

	public void addOptions(Collection<Option> options) {
		if(this.options == null)
			this.options = new ArrayList<Option>();
		this.options.addAll(options);
	}
	
	public void setOptions(List<Option> options) {
		if(this.options == null)
			this.options = new ArrayList<Option>();
		if(options == null)
			options = new ArrayList<Option>();
		this.options = options;
	}

	public List<Group> getGroups() {
		return Collections.unmodifiableList(groups);
	}

	public void setGroups(List<Group> groups) {
		if(groups == null)
			groups = new ArrayList<Group>();
		this.groups = groups;
	}
	
	public void addGroup(Group group) {
		if(this.groups == null)
			this.groups = new ArrayList<Group>();
		this.groups.add(group);
	}

	public void addGroups(Collection<Group> groups) {
		if(this.groups == null)
			this.groups = new ArrayList<Group>();
		this.groups.addAll(groups);
	}

	/**
	 * Execute the query and returns the results.
	 * 
	 * @return The results as a grouped collection of activities.
	 */
	public Groupable<Activity> get() {
		// Initialize fields
		ofy = Registry.dao().begin();
		results = new ResultSet(ofy);
		subQueryFactory = new SubQueryFactory(ofy);
		subQueries = new HashMap<Class<?>, SubQuery<?>>();
		optionKinds = getOptionKinds();
		groupKinds = getGroupKinds();

		// Group on kinds
		optionsByKind = getOptionsByKind();
		groupsByKind = getGroupsByKind();

		// Create sub queries and add options
		for (Class<?> kind : optionKinds.values()) {
			createSubQuery(kind);
		}

		// Fetch activity slices
		List<ActivitySlice> slices = fetchSlices();

		// Start grouping
		Groupable<ActivitySlice> sliceResults = new GroupableLeaf<ActivitySlice>(
				null, slices);

		// Apply slices grouping
		Class<?> sliceGroupKind = groupKinds.get(ActivitySlice.class);
		if (sliceGroupKind != null) {
			List<Group> sliceGroups = groupsByKind.get(sliceGroupKind);
			for (Group group : sliceGroups) {
				sliceResults = sliceResults.group(group);
			}
		}

		// Merge slices into activities
		Class<?> activityOptionKind = optionKinds.get(Activity.class);
		Groupable<Activity> activityResults = sliceResults
				.transform(new FuncMergeSlices(activityOptionKind));

		// Process activities
		activityResults = activityResults
				.transform(new FuncProcessActivities());

		// Apply activities grouping
		Class<?> activityGroupKind = groupKinds.get(Activity.class);
		if (activityGroupKind != null) {
			List<Group> activityGroups = groupsByKind.get(activityGroupKind);
			for (Group group : activityGroups) {
				activityResults = activityResults.group(group);
			}
		}

		return activityResults;
	}

	private Map<Class<?>, Class<?>> getOptionKinds() {
		Set<Class<?>> kinds = new HashSet<Class<?>>(defaultOptionKinds);
		for (Option option : options) {
			kinds.add(option.getKind());
		}
		return translateKinds(kinds);
	}

	private Map<Class<?>, Class<?>> getGroupKinds() {
		Set<Class<?>> kinds = new HashSet<Class<?>>(defaultGroupKinds);
		for (Group group : groups) {
			kinds.add(group.getKind());
		}
		return translateKinds(kinds);
	}

	/**
	 * Creates a sub query for a given kind with the given options applied.
	 * 
	 * @param kind
	 *            The sub query kind.
	 * @param options
	 *            The sub query options to be applied.
	 * @return The sub query.
	 */
	private SubQuery<?> createSubQuery(Class<?> kind) {
		// Create sub query and add options
		SubQuery<?> subQuery = subQueryFactory.create(kind);
		subQuery.addOptions(options);
		// Execute and store sub query
		results.add(kind, subQuery.execute());
		subQueries.put(kind, subQuery);
		return subQuery;
	}

	/**
	 * Fetch the activity slices from the sub queries.
	 * 
	 * @return The resulting list of slices.
	 */
	private List<ActivitySlice> fetchSlices() {
		// Fetch activity slices
		List<Set<Key<ActivitySlice>>> sliceSets = new ArrayList<Set<Key<ActivitySlice>>>();
		for (SubQuery<?> subQuery : subQueries.values()) {
			sliceSets.add(subQuery.fetchSlices(results));
		}

		// Find intersection of activity slices
		Set<Key<ActivitySlice>> sliceKeys = intersect(sliceSets);

		// Retrieve activity slices
		List<ActivitySlice> slices = new ArrayList<ActivitySlice>(results
				.fetch(ActivitySlice.class, sliceKeys).values());

		return slices;
	}

	/**
	 * Translate a set of kinds into a map from kinds to super kinds.
	 * 
	 * <p>
	 * When a kind is a subclass of another kind, the kind is mapped to that
	 * subclass. This allows actions on super classes to be translated to
	 * actions on sub classes.
	 * 
	 * @param kinds
	 *            The set of kinds to translate.
	 * @return The translation map.
	 */
	private Map<Class<?>, Class<?>> translateKinds(Set<Class<?>> kinds) {
		Map<Class<?>, Class<?>> translatedKinds = new HashMap<Class<?>, Class<?>>();

		// Remove superclasses and retain subclasses
		Set<Class<?>> kindsToRemove = new HashSet<Class<?>>();
		for (Class<?> kind : kinds) {
			// Skip if already removed
			if (kindsToRemove.contains(kind))
				continue;
			// Default to same kind
			if (!translatedKinds.containsKey(kind)) {
				translatedKinds.put(kind, kind);
			}
			for (Class<?> otherKind : kinds) {
				// Skip if same kind or already removed
				if (kind.equals(otherKind) || kindsToRemove.contains(otherKind))
					continue;
				if (kind.isAssignableFrom(otherKind)) {
					// If other kind inherits from kind
					// Then other kind is the subclass
					translatedKinds.put(kind, otherKind);
				} else if (kind.isAssignableFrom(otherKind)) {
					// If kind inherits from other kind
					// Then kind is the subclass
					translatedKinds.put(otherKind, kind);
				}
			}
		}

		return translatedKinds;
	}

	private Map<Class<?>, List<Option>> getOptionsByKind() {
		Map<Class<?>, List<Option>> optionsByKind = new HashMap<Class<?>, List<Option>>();
		for (Option option : options) {
			Class<?> kind = optionKinds.get(option.getKind());
			if (!optionsByKind.containsKey(kind)) {
				optionsByKind.put(kind, new ArrayList<Option>());
			}
			optionsByKind.get(kind).add(option);
		}

		return optionsByKind;
	}

	private Map<Class<?>, List<Group>> getGroupsByKind() {
		Map<Class<?>, List<Group>> groupsByKind = new HashMap<Class<?>, List<Group>>();
		for (Group group : groups) {
			Class<?> kind = groupKinds.get(group.getKind());
			if (!groupsByKind.containsKey(kind)) {
				groupsByKind.put(kind, new ArrayList<Group>());
			}
			groupsByKind.get(kind).add(group);
		}

		return groupsByKind;
	}

	/**
	 * Determine the intersection of a number of sets, i.e. only entries which
	 * appear in <i>all</i> sets are retained.
	 * 
	 * @param sets
	 *            A collection of sets.
	 * @return the intersection set.
	 */
	private <T> Set<T> intersect(Iterable<Set<T>> sets) {
		Set<T> results = new HashSet<T>();
		for (Set<T> set : sets) {
			if (results.isEmpty()) {
				results.addAll(set);
			} else {
				results.retainAll(set);
			}
		}
		return results;
	}

	/**
	 * Merge slices into activities.
	 * 
	 * <p>
	 * This method transforms slices into activities and attempts to join
	 * 
	 * @param slices
	 * @return The list of merged activities.
	 */
	protected static List<Activity> mergeSlices(Iterable<ActivitySlice> slices,
			ResultSet results, Class<Activity> activityKind) {

		// Group by original activity, using identifier as key
		Map<Long, List<Activity>> map = new LinkedHashMap<Long, List<Activity>>();
		Map<Key<Activity>, Activity> fullActivities = results
				.fetch(activityKind);

		// Iterate over all slices
		for (ActivitySlice slice : slices) {
			// Get activity identifier
			Key<Activity> activityKey = slice.getActivity();
			Long activityId = activityKey.getId();
			// Get full activity
			Activity fullActivity = fullActivities.get(activityKey);
			List<Activity> activities;
			Activity activity;

			if (map.containsKey(activityId)) {
				// Get the current list of activities for this key
				activities = map.get(activityId);
				// Try to use the last activity
				activity = activities.get(activities.size() - 1);
			} else {
				// Create a new list of activities for this key
				activities = new ArrayList<Activity>();
				// Clone this activity
				activity = (Activity) fullActivity.clone();
				// Make it start at the same date as the slice
				// but with a zero duration
				activity.setStart(slice.getDate());
				activity.setEnd(slice.getDate());
				// Store in list and map
				activities.add(activity);
				map.put(activityId, activities);
			}

			// Try to append the slice
			boolean isAppended = activity.addSlice(slice);
			if (!isAppended) {
				// The slice could not be added to the activity
				// Clone the activity and make it match
				// the time span of the slice
				activity = (Activity) activity.clone();
				activity.setStart(slice.getDate());
				activity.setDuration(slice.getDuration());
				activities.add(activity);
			}
		}

		// Unite all activity lists into one flat list
		List<Activity> flatActivities = new ArrayList<Activity>();
		for (List<Activity> activities : map.values()) {
			flatActivities.addAll(activities);
		}

		return flatActivities;
	}

	/**
	 * Function to merge slices into activities.
	 */
	protected class FuncMergeSlices implements
			Function<List<ActivitySlice>, List<Activity>> {
		private Class<Activity> activityKind;

		@SuppressWarnings("unchecked")
		public FuncMergeSlices(Class<?> activityKind) {
			this.activityKind = (Class<Activity>) activityKind;
		}

		@Override
		public List<Activity> apply(List<ActivitySlice> input) {
			return mergeSlices(input, results, activityKind);
		}
	}

	/**
	 * Process the resulting list of activities.
	 * 
	 * @param activities
	 *            The list of activities.
	 */
	protected List<Activity> processActivities(List<Activity> activities) {
		// Let sub queries process activities
		for (SubQuery<?> subQuery : subQueries.values()) {
			subQuery.process(activities);
		}
		// Return new activities
		return activities;
	}

	/**
	 * Function to process resulting activities.
	 */
	protected class FuncProcessActivities implements
			Function<List<Activity>, List<Activity>> {
		@Override
		public List<Activity> apply(List<Activity> input) {
			return processActivities(input);
		}
	}

}

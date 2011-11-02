package projectatlast.query;

import projectatlast.Activity;

import java.util.*;

public class Group {

	private SortField sortField;

	public Group(SortField sortField) {
		this.sortField = sortField;
	}

	public Map<Object, List<Activity>> group(List<Activity> activities) {
		Map<Object, List<Activity>> grouped = new LinkedHashMap<Object, List<Activity>>();
		Iterator<Activity> it = activities.iterator();

		while (it.hasNext()) {
			Activity activity = it.next();

			// the retrieved value from the activity
			Object value = sortField.getValue(activity);

			// does group with name 'value' exist?
			if (grouped.containsKey(value)) {
				// edit the List<Activity>

				// get the list and add the activity
				grouped.get(value).add(activity);
			} else {
				// put new item -> create new group
				List<Activity> groupedActivities = new ArrayList<Activity>();
				groupedActivities.add(activity);
				grouped.put(value, groupedActivities);
			}
		}

		return grouped;
	}

}

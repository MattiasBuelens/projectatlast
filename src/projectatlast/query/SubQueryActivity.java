package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class SubQueryActivity extends SubQuery<Activity> {

	public SubQueryActivity(Class<?> kind, Objectify ofy) {
		super(kind, ofy);
	}

	@Override
	public Set<Key<ActivitySlice>> fetchSlices(ResultSet results) {
		// Fetch all slices and clone the returned map
		Map<Key<ActivitySlice>, ActivitySlice> slices = new HashMap<Key<ActivitySlice>, ActivitySlice>(
				results.fetch(ActivitySlice.class));
		// Fetch all activity keys
		Set<Key<Activity>> activityKeys = results.fetchKeys(kind);

		// Iterate over the slices
		Iterator<Map.Entry<Key<ActivitySlice>, ActivitySlice>> it = slices
				.entrySet().iterator();
		while (it.hasNext()) {
			// Get the key of the parent activity
			ActivitySlice slice = it.next().getValue();
			Key<Activity> activityKey = slice.getActivity();
			// If the parent is not in the activities result set
			if (!activityKeys.contains(activityKey)) {
				// Remove the slice from the map
				it.remove();
			}
		}
		// Return the keys from the slices
		return slices.keySet();
	}

}

package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class SubQueryActivity<A extends Activity> extends SubQuery<A> {

	public SubQueryActivity(Class<?> kind, Objectify ofy) {
		super(kind, ofy);
	}

	/**
	 * Fetch keys of activity slices belonging to the activities from the raw
	 * results set.
	 * 
	 * For activity entities, the following strategy is followed:<ol>
	 * <li>Collect all activity keys and slices from the results set.</li>
	 * <li>Only retain slices whose activity appears in the activity results set.</li>
	 * </ol>
	 * @param results
	 *            Raw results from sub query.
	 * @return Set of activity slice keys.
	 */
	@Override
	public Set<Key<ActivitySlice>> fetchSlices(ResultSet results) {
		// Fetch all slices and clone the returned map
		Map<Key<ActivitySlice>, ActivitySlice> slices = new HashMap<Key<ActivitySlice>, ActivitySlice>(
				results.fetch(ActivitySlice.class));
		// Fetch all activity keys
		Set<Key<A>> activityKeys = results.fetchKeys(kind);

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

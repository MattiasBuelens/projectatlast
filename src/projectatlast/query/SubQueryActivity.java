package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.util.*;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class SubQueryActivity extends SubQuery<Activity> {

	public SubQueryActivity(Class<?> kind, Objectify ofy) {
		super(kind, ofy);
	}

	@Override
	public Set<Key<ActivitySlice>> fetchSlices(
			ResultSet results) {
		Set<Key<ActivitySlice>> keys = new HashSet<Key<ActivitySlice>>();
		for(Activity activity : results.fetch(kind).values()) {
			// Add all slice keys
		}
		return keys;
	}

	@Override
	protected List<Activity> fetchActivities(ResultSet results) {
		// TODO Auto-generated method stub
		return null;
	}

}

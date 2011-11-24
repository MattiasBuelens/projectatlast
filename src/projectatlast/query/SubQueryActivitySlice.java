package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class SubQueryActivitySlice extends SubQuery<ActivitySlice> {

	public SubQueryActivitySlice(Class<?> kind, Objectify ofy) {
		super(kind, ofy);
	}

	@Override
	public Set<Key<ActivitySlice>> fetchSlices(ResultSet results) {
		return results.fetchKeys(kind);
	}

	@Override
	protected List<Activity> fetchActivities(ResultSet results) {
		// TODO Auto-generated method stub
		return null;
	}

}

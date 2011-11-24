package projectatlast.query;

import projectatlast.tracking.ActivitySlice;

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

}

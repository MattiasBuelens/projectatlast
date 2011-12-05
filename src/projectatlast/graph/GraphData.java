package projectatlast.graph;

import projectatlast.data.JSONable;
import projectatlast.group.Group;

import java.util.*;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public abstract class GraphData implements JSONable {

	protected List<Group> groups;

	protected GraphData() {
		this.groups = new ArrayList<Group>();
	}

	protected GraphData(List<Group> groups) {
		this.groups = groups;
	}

	protected List<String> formatGroupNames(Group group, Iterable<Object> keys) {
		List<String> names = new ArrayList<String>();
		for (Object key : keys) {
			names.add(group.getField().formatValue(key));
		}
		return names;
	}

	public abstract JSONObject toJSON(JSONObject json) throws JSONException;
}

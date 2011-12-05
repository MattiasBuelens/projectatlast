package projectatlast.graph;

import projectatlast.data.JSONable;
import projectatlast.group.Grouped;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public interface GraphData extends JSONable {
	public Grouped<Long> getData();
	public JSONObject toJSON(JSONObject json) throws JSONException;
}

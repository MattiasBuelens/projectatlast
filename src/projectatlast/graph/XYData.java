/**
 * A holder for pairs numbers (X & Y values)
 */
package projectatlast.graph;

import projectatlast.group.Group;
import projectatlast.group.Grouped;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.org.json.*;

public class XYData extends GraphData {

	Grouped<Double> data;

	public XYData(Group group, Grouped<Double> data) {
		super();
		this.groups.add(group);
		this.data = data;
	}

	public List<Object> getX() {
		return new ArrayList<Object>(data.getKeys());
	}

	public List<Double> getY() {
		return data.getValues();
	}
	
	/**
	 * Get all formatted group names.
	 * 
	 * @return List of group names.
	 */
	public List<String> getGroupNames() {
		return formatGroupNames(groups.get(0), getX());
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		return toJSON(json);
	}

	@Override
	public JSONObject toJSON(JSONObject json) throws JSONException {
		json.put("x", new JSONArray(getGroupNames()));
		json.put("y", new JSONArray(getY()));
		return json;
	}

}

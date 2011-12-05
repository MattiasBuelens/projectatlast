/**
 * A holder for pairs numbers (X & Y values)
 */
package projectatlast.graph;

import projectatlast.group.Grouped;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.org.json.*;

public class XYData implements GraphData{

	Grouped<Long> data;

	public XYData(Grouped<Long> data) {
		this.data = data;
	}

	public List<Object> getX() {
		return new ArrayList<Object>(data.getKeys());
	}

	public List<Long> getY() {
		return data.getValues();
	}

	@Override
	public Grouped<Long> getData() {
		return data;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		return toJSON(json);
	}

	@Override
	public JSONObject toJSON(JSONObject json) throws JSONException {
		json.put("x", new JSONArray(getX()));
		json.put("y", new JSONArray(getY()));
		return json;
	}

}

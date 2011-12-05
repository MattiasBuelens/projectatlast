package projectatlast.graph;

import projectatlast.group.Grouped;

import java.util.List;

import com.google.appengine.repackaged.org.json.*;

public class ScatterData extends GraphData {

	Grouped<Long> dataX;
	Grouped<Long> dataY;

	public ScatterData(Grouped<Long> dataX, Grouped<Long> dataY) {
		this.dataX = dataX;
		this.dataY = dataY;
	}

	public List<Long> getX() {
		return dataX.getValues();
	}

	public List<Long> getY() {
		return dataY.getValues();
	}

	public Grouped<Long> getDataX() {
		return dataX;
	}

	public Grouped<Long> getDataY() {
		return dataY;
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

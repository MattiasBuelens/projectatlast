package projectatlast.graph;

import projectatlast.group.Grouped;

import java.util.List;

import com.google.appengine.repackaged.org.json.*;

public class ScatterData extends GraphData {

	Grouped<Double> dataX;
	Grouped<Double> dataY;

	public ScatterData(Grouped<Double> dataX, Grouped<Double> dataY) {
		this.dataX = dataX;
		this.dataY = dataY;
	}

	public List<Double> getX() {
		return dataX.getValues();
	}

	public List<Double> getY() {
		return dataY.getValues();
	}

	public Grouped<Double> getDataX() {
		return dataX;
	}

	public Grouped<Double> getDataY() {
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

package projectatlast.graph;

import projectatlast.group.Grouped;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.org.json.*;

public class ScatterData implements GraphData{

	Grouped<Long> dataX;
	Grouped<Long> dataY;
	
	public ScatterData(Grouped<Long> dataX, Grouped<Long> dataY) {
		this.dataX=dataX;
		this.dataY=dataY;
	}



	public List<Object> getX() {
		return new ArrayList<Object>(dataX.getValues());
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



	@Override
	public Grouped<Long> getData() {
		// TODO Auto-generated method stub
		return null;
	}

}

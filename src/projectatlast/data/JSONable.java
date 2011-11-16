package projectatlast.data;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;

public interface JSONable {
	public JSONObject toJSON() throws JSONException;
}

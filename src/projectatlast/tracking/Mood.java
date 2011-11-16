package projectatlast.tracking;

import projectatlast.data.JSONable;

import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Mood implements JSONable {

	@Id	Long id;
	long comprehension;
	long interest;

	protected Mood() { }

	public Mood(long comprehension, long interest) {
		super();
		setComprehension(comprehension);
		setInterest(interest);
	}

	public long getId() {
		return id;
	}

	public long getComprehension() {
		return comprehension;
	}

	public void setComprehension(long comprehension) {
		this.comprehension = comprehension;
	}

	public long getInterest() {
		return interest;
	}

	public void setInterest(long interest) {
		this.interest = interest;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		//json.put("id", id);
		json.put("comprehension", comprehension);
		json.put("interest", interest);
		return json;
	}
}

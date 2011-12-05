package projectatlast.tracking;

import projectatlast.data.JSONable;

import javax.persistence.Embeddable;
import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Entity;

public class Mood implements JSONable {

	long comprehension;
	long interest;

	protected Mood() { }

	public Mood(long comprehension, long interest) {
		super();
		setComprehension(comprehension);
		setInterest(interest);
	}

	public long getComprehension() {
		return comprehension;
	}

	public void setComprehension(long comprehension) {
		if ((Long)comprehension == null){
			this.comprehension = 50;
		}
			this.comprehension = comprehension;
	}

	public long getInterest() {
		return interest;
	}

	public void setInterest(long interest) {
		if ((Long)interest == null){
			this.interest = 50;
		}
		this.interest = interest;
	}
	
	public void setMood(long interest, long comprehension){
		this.interest= interest;
		this.comprehension = comprehension; 
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("comprehension", getComprehension());
		json.put("interest", getInterest());
		return json;
	}
}

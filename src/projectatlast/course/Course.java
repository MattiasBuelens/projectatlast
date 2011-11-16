package projectatlast.course;

import projectatlast.data.JSONable;

import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Entity
@Cached
public class Course implements JSONable {
	@Id	String id;
	String name;
	int credits;

	protected Course() { }

	public Course(String id, String name, int credits) {
		this();
		this.id = id;
		this.name = name;
		this.credits = credits;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	@Override
	public boolean equals(Object obj) {
		// Shortcut: identical reference
		if (this == obj)
			return true;
		// Shortcut: incompatible type
		if (!(obj instanceof Course))
			return false;
		// id must be equal
		Course otherCourse = (Course) obj;
		return this.id.equals(otherCourse.id);
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash = (id != null) ? id.hashCode() : 0;
		return hash;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + this.id + "](" + this.name + ")";
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("name", name);
		json.put("credits", credits);
		return json;
	}
}
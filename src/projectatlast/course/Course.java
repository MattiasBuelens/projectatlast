package projectatlast.course;

import projectatlast.data.JSONable;

import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.annotation.*;

@Entity
@Cached
@Unindexed
public class Course implements JSONable, Comparable<Course> {
	@Id String id;
	String name;
	int credits;

	protected Course() {}

	public Course(String id, String name, int credits) {
		this();
		this.id = id;
		setName(name);
		setCredits(credits);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	private void setCredits(int credits) {
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
		// Identifiers must be equal
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
		return "[" + this.id + "](" + this.name + ")";
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("name", getName());
		json.put("credits", getCredits());
		return json;
	}

	@Override
	public int compareTo(Course course) {
		return this.getName().compareToIgnoreCase(course.getName());
	}
}
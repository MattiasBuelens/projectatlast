package projectatlast.course;

import projectatlast.data.JSONable;
import projectatlast.data.Registry;

import java.util.*;

import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Entity
@Cached
public class StudyProgram implements JSONable, Comparable<StudyProgram> {
	@Id String id;
	Set<Key<Course>> courses;
	String name;

	protected StudyProgram() {}

	public StudyProgram(String id, String name) {
		this.id = id;
		this.name = name;
		this.courses = new HashSet<Key<Course>>();
	}

	public StudyProgram(String id, String name, Set<Key<Course>> courses) {
		this(id, name);
		setCourseKeys(courses);
	}

	public StudyProgram(String id, String name, Iterable<Course> courses) {
		this(id, name);
		setCourses(courses);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Course> getCourses() {
		return Registry.courseFinder().getCourses(courses);
	}

	public void setCourseKeys(Collection<Key<Course>> courses) {
		this.courses.clear();
		this.courses.addAll(courses);
	}

	public void setCourses(Iterable<Course> courses) {
		setCourseKeys(Registry.courseFinder().getKeys(courses));
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		json.put("name", getName());
		JSONArray courses = new JSONArray();
		for (Course course : getCourses()) {
			courses.put(course.toJSON());
		}
		json.put("courses", courses);
		return json;
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
	public int compareTo(StudyProgram program) {
		return this.getName().compareToIgnoreCase(program.getName());
	}
}

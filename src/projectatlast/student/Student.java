package projectatlast.student;

import projectatlast.data.Registry;
import projectatlast.tracking.*;

import java.util.List;

import javax.persistence.*;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Student {

	@Id Long id;
	User user;
	List<Key<Course>> courses;
	boolean configured;
	Key<Activity> activity;
	@Transient List<Course> courseObjects;
	
	protected Student() { }

	public Student(User user) {
		this.user = user;
		this.configured = false;
	}

	public boolean addCourse(Key<Course> courseKey) {
		return courses.add(courseKey);
	}

	public List<Course> getCourses() {
		if(courseObjects == null) {
			courseObjects = Registry.courseFinder().getCourses(courses);
		}
		return courseObjects;
	}

	/*public List<Key<Course>> getCourseKeys() {
		return courses;
	}

	public void setCourseKeys(List<Key<Course>> courseKeys) {
		this.courses = courseKeys;
	}*/

	@PostLoad
	@SuppressWarnings("unused")
	private void clearTransients() {
		courseObjects = null;
	}

	public boolean getConfigured() {
		return configured;
	}

	public Key<Activity> getActivity() {
		return activity;
	}

	public void setActivity(Key<Activity> activity) {
		this.activity = activity;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}
}

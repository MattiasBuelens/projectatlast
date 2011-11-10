package projectatlast.student;

import projectatlast.data.Registry;

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

	@Transient List<Course> courseObjects;
	
	protected Student() { }

	public Student(User user) {
		this();
		this.user = user;
		this.configured=false;
	}

	public boolean addCourse(Key<Course> courseKey) {
		return courses.add(courseKey);
	}

	public List<Course> getCourses() {
		if(courseObjects == null) {
			courseObjects = Registry.courseFinder().getCourses(this);
		}
		return courseObjects;
	}

	public List<Key<Course>> getCourseKeys() {
		return courses;
	}

	public void setCourseKeys(List<Key<Course>> courseKeys) {
		this.courses = courseKeys;
	}

	@PostLoad
	@SuppressWarnings("unused")
	private void clearTransients() {
		courseObjects = null;
	}

	public boolean getConfigured() {
		// TODO Auto-generated method stub
		return configured;
	}
}

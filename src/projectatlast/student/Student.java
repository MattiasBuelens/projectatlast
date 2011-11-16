package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.*;

import javax.persistence.Id;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Entity
@Cached
public class Student {

	@Id	Long id;
	User user;
	Set<Key<Course>> courses;
	boolean configured;
	Key<Activity> activity;

	protected Student() { }

	public Student(User user) {
		this.user = user;
		this.configured = false;
		this.courses = new HashSet<Key<Course>>();
	}

	public List<Course> getCourses() {
		return Registry.courseFinder().getCourses(courses);
	}

	public void setCourseKeys(Collection<Key<Course>> courses) {
		this.courses.clear();
		this.courses.addAll(courses);
	}

	public void setCourses(Collection<Course> courses) {
		setCourseKeys(Registry.courseFinder().getKeys(courses));
	}

	public Key<Activity> getActivity() {
		return activity;
	}

	public void setActivity(Key<Activity> activity) {
		this.activity = activity;
	}

	public boolean isConfigured() {
		return configured;
	}

	public void setConfigured(boolean configured) {
		this.configured = configured;
	}

	public boolean isInActivity() {
		return (activity != null);
	}

}

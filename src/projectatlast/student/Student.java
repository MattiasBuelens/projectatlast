package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.*;

import javax.persistence.Id;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

// @Cached
public class Student {

	@Id
	Long id;
	User user;
	Set<Key<Course>> courses;
	boolean configured;
	Key<Activity> activity;
	//@Unindexed
	List<String> tools;

	protected Student() {
	}

	public Student(User user) {
		this.user = user;
		this.configured = false;
		this.tools = getDefaultTools();
	}

	/**
	 * Get the datastore identifier for this student.
	 * 
	 * @return The identifier.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the associated Google User for this student.
	 * 
	 * @return The user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Get a list of all enrolled courses of the student.
	 * 
	 * @return List of enrolled courses.
	 */
	public List<Course> getCourses() {
		return Registry.courseFinder().getCourses(courses);
	}

	/**
	 * Set the course keys of the student.
	 * 
	 * @param courses
	 *            Keys of enrolled courses.
	 */
	private void setCourseKeys(Collection<Key<Course>> courses) {
		if (this.courses == null) {
			this.courses = new HashSet<Key<Course>>();
		}
		this.courses.clear();
		this.courses.addAll(courses);
	}

	/**
	 * Set the enrolled courses of the student.
	 * 
	 * @param courses
	 *            List of enrolled courses.
	 */
	public void setCourses(Collection<Course> courses) {
		setCourseKeys(Registry.courseFinder().getKeys(courses));
	}

	/**
	 * Checks whether the student has any enrolled courses.
	 * 
	 * @return true if the student has enrolled courses, false if he has none.
	 */
	public boolean hasCourses() {
		return (courses != null && !courses.isEmpty());
	}

	/**
	 * Get the current activity of the student.
	 * 
	 * @return The current activity, or null if no current activity.
	 */
	public Activity getActivity() {
		return Registry.activityFinder().getActivity(activity);
	}

	/**
	 * Set the current activity of the student.
	 * 
	 * @param activity
	 *            Current activity. Set to null to remove the current activity.
	 */
	public boolean setActivity(Activity activity) {
		Key<Activity> activityKey;
		if (activity != null) {
			activityKey = Registry.activityFinder().getKey(activity);
			if (activityKey == null)
				return false;
		} else {
			activityKey = null;
		}
		this.activity = activityKey;
		return true;
	}

	/**
	 * Checks whether the student is doing any activities at the moment.
	 * 
	 * @return true if in an activity, false if not.
	 */
	public boolean isInActivity() {
		return (activity != null);
	}

	/**
	 * Check whether the student is configured.
	 * 
	 * @return true if configured, false if not.
	 */
	public boolean isConfigured() {
		return configured;
	}

	/**
	 * Set whether the student is configured.
	 * 
	 * @param isConfigured
	 *            true if configured, false if not.
	 */
	public void setConfigured(boolean isConfigured) {
		this.configured = isConfigured;
	}

	public List<String> getTools() {
		if (this.tools == null)
			this.tools = getDefaultTools();
		return this.tools;
	}

	public void setTools(List<String> tools) {
		if (this.tools == null) {
			this.tools = new ArrayList<String>();
		}
		if (tools == null || tools.isEmpty()) {
			tools = getDefaultTools();
		}
		this.tools.clear();
		this.tools.addAll(tools);
	}

	public void addTool(String tool) {
		if (this.tools == null)
			this.tools = getDefaultTools();
		this.tools.add(tool);
	}

	public List<String> getDefaultTools() {
		List<String> tools = new ArrayList<String>();
		tools.add("Pen and paper");
		tools.add("Computer");
		tools.add("Music");
		tools.add("Snacks");
		return tools;
	}

}

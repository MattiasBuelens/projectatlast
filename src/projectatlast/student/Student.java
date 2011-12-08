package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.*;

import javax.persistence.Id;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Unindexed;

public class Student {

	@Id Long id;
	User user;
	Set<Key<Course>> courses;
	boolean configured;
	Key<Activity> activity;
	@Unindexed List<String> tools;
	@Unindexed List<String> freeTimeTypes;
	@Unindexed List<String> locations;

	protected Student() {}

	public Student(User user) {
		this.user = user;
		this.configured = false;
		this.tools = getDefaultTools();
		this.freeTimeTypes = getDefaultFreeTimeTypes();
		this.locations = getDefaultLocations();
	}

	/**
	 * Get the identifier for this student.
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

	/*
	 * Study tools
	 */
	public List<String> getDefaultTools() {
		List<String> tools = new ArrayList<String>();
		tools.add("Pen and paper");
		tools.add("Computer");
		tools.add("Music");
		tools.add("Snacks");
		return tools;
	}

	public List<String> getDefaultLocations() {
		List<String> locations = new ArrayList<String>();
		locations.add("Kot");
		locations.add("Home");
		locations.add("Library");
		locations.add("Campus");
		return locations;
	}

	public List<String> getTools() {
		if (tools == null)
			tools = getDefaultTools();
		return tools;
	}

	public void setTools(Collection<String> newTools) {
		getTools().clear();
		tools.addAll(newTools);
	}

	public boolean addTool(String tool) {
		List<String> tools = getTools();
		return tool != null && !tool.isEmpty() && !tools.contains(tool)
				&& tools.add(tool);
	}

	public boolean removeTools(Collection<String> removeTools) {
		return tools.removeAll(removeTools);
	}

	public boolean removeTools(String[] removeTools) {
		return removeTools(Arrays.asList(removeTools));
	}

	/*
	 * Free time activity types
	 */
	public List<String> getDefaultFreeTimeTypes() {
		List<String> types = new ArrayList<String>();
		types.add("Bar");
		types.add("Gaming");
		types.add("Music");
		types.add("Sports");
		return types;
	}

	public List<String> getFreeTimeTypes() {
		if (freeTimeTypes == null)
			freeTimeTypes = getDefaultFreeTimeTypes();
		return freeTimeTypes;
	}

	public void setFreeTimeTypes(Collection<String> newTypes) {
		getFreeTimeTypes().clear();
		freeTimeTypes.addAll(newTypes);
	}

	public boolean addFreeTimeType(String type) {
		List<String> types = getFreeTimeTypes();
		return type != null && !type.isEmpty() && !types.contains(type)
				&& types.add(type);
	}

	public boolean removeFreeTimeTypes(Collection<String> removeTypes) {
		return freeTimeTypes.removeAll(removeTypes);
	}

	public boolean removeFreeTimeTypes(String[] removeTypes) {
		return removeFreeTimeTypes(Arrays.asList(removeTypes));
	}

	/*
	 * Locations
	 */
	public List<String> getLocations() {
		if (locations == null)
			locations = getDefaultLocations();
		return locations;
	}

	public void setlocations(Collection<String> newLocations) {
		getLocations().clear();
		locations.addAll(newLocations);
	}

	public boolean addLocation(String location) {
		List<String> locations = getLocations();
		return location != null && !location.isEmpty()
				&& !locations.contains(location) && locations.add(location);
	}

	public boolean removeLocations(Collection<String> removeLocations) {
		return locations.removeAll(removeLocations);
	}

	public boolean removeLocations(String[] removeLocations) {
		return removeLocations(Arrays.asList(removeLocations));
	}

	@Override
	public boolean equals(Object obj) {
		// Shortcut: identical reference
		if (this == obj)
			return true;
		// Shortcut: incompatible type
		if (!(obj instanceof Student))
			return false;
		// Identifiers must be equal
		Student otherStudent = (Student) obj;
		return this.id == otherStudent.id;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash = (id != null) ? id.hashCode() : 0;
		return hash;
	}
}

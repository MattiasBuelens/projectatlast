package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.List;

public class StudentController {

	/**
	 * Get the current activity of a student.
	 * 
	 * @param student
	 *            the student
	 * @return the activity, or null if no running activity.
	 */
	public static Activity getCurrentActivity(Student student) {
		if (student == null)
			return null;
		return student.getActivity();
	}

	/**
	 * Set the current activity on a student.
	 * 
	 * @param student
	 *            the student
	 * @param activity
	 *            the running activity
	 * @return true if successful, false otherwise.
	 */
	public static boolean setCurrentActivity(Student student, Activity activity) {
		boolean result = student.setActivity(activity);
		result = result && put(student);
		return result;
	}

	/**
	 * Get all courses of a student.
	 * 
	 * @param student
	 *            the student
	 * @return list of courses
	 */
	public static List<Course> getCourses(Student student) {
		return student.getCourses();
	}

	/**
	 * Set whether the student is configured.
	 * 
	 * @param student
	 *            the student
	 * @param isConfigured
	 *            true means the student has completed configuration, false if
	 *            he has not.
	 * @return true if successful, false otherwise.
	 */
	public static boolean setConfigured(Student student, boolean isConfigured) {
		student.setConfigured(isConfigured);
		Registry.studentFinder().put(student);
		return true;
	}

	/**
	 * Set the student as configured.
	 * 
	 * @param student
	 *            the student
	 * @return true if successful, false otherwise.
	 */
	public static boolean setConfigured(Student student) {
		return setConfigured(student, true);
	}

	/**
	 * Set the courses on a student.
	 * 
	 * @param student
	 *            the student
	 * @param courses
	 *            the new courses
	 * @return true if success, false otherwise.
	 */
	public static boolean setCourses(Student student, List<Course> courses) {
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}

	/**
	 * Set the courses on a student.
	 * 
	 * @param student
	 *            the student
	 * @param courseIds
	 *            the identifiers of the new courses
	 * @return true if success, false otherwise.
	 */
	public static boolean setCoursesById(Student student,
			Iterable<String> courseIds) {
		List<Course> courses = Registry.courseFinder()
				.getCoursesById(courseIds);
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}

	/*
	 * Tools
	 */
	public static List<String> getTools(Student student) {
		if (student == null) {
			return null;
		}
		return student.getTools();
	}

	public static boolean addTools(Student student, String[] tools) {
		boolean result = false;
		if (tools != null) {
			for (String tool : tools) {
				result = result || student.addTool(tool);
			}
		}
		result = result && put(student);
		return result;
	}

	public static boolean removeTools(Student student, String[] toolsToRemove) {
		boolean result = false;
		if (toolsToRemove != null) {
			result = student.removeTools(toolsToRemove);
		}
		result = result && put(student);
		return result;
	}

	/*
	 * Locations
	 */
	public static List<String> getLocations(Student student) {
		if (student == null) {
			return null;
		}
		return student.getLocations();
	}

	public static boolean addLocation(Student student, String location) {
		boolean result = false;
		if (location != null && location != "") {
			result = student.addLocation(location);
		}
		result = result && put(student);
		return result;
	}

	public static boolean removeLocations(Student student,
			String[] locationsToRemove) {
		boolean result = false;
		if (locationsToRemove != null) {
			result = student.removeLocations(locationsToRemove);
		}
		result = result && put(student);
		return result;
	}

	/*
	 * Free time activity types
	 */
	/**
	 * Get all free time activity types of a student.
	 * 
	 * @param student
	 *            the student
	 * @return list of free time activity types
	 */
	public static List<String> getFreeTimeTypes(Student student) {
		if (student == null) {
			return null;
		}
		return student.getFreeTimeTypes();
	}

	public static boolean addFreeTimeType(Student student, String newType) {
		boolean result = true;
		if (newType != null && newType != "") {
			result = student.addFreeTimeType(newType);
		}
		result = result && put(student);
		return result;
	}

	public static boolean removeFreeTimeTypes(Student student,
			String[] typesToRemove) {
		boolean result = true;
		if (typesToRemove != null) {
			result = student.removeFreeTimeTypes(typesToRemove);
		}
		result = result && put(student);
		return result;
	}

	protected static boolean put(Student student) {
		return Registry.studentFinder().put(student);
	}
}

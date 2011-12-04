package projectatlast.student;

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

	public static List<String> getTools(Student student) {
		if(student == null) {
			return null;
		}
		return student.getTools();
	}

	public static boolean addTool(Student student, String extraTool) {
		boolean result = true;
		if (extraTool != null && extraTool != "") {
			result = student.addTool(extraTool);
		}
		result = result && put(student);
		return result;
	}

	public static boolean removeTools(Student student, String[] toolsToRemove) {
		boolean result = true;
		if (toolsToRemove != null) {
			result = student.removeTools(toolsToRemove);
		}
		result = result && put(student);
		return result;
	}
	
	protected static boolean put(Student student) {
		return Registry.studentFinder().put(student);
	}
}

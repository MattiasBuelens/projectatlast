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
		if(result) {
			Registry.studentFinder().put(student);
		}
		return result;
	}
	
	public static List<String> getTools(Student student){
		return student.getTools();
	}
	
	public static void addTool(Student student, String extraTool){
		boolean result = true;
		if(extraTool != null && extraTool !=""){
			result = student.addTool(extraTool);
		}
		if(result) {
			Registry.studentFinder().put(student);
		}
	}
	
	public static void removeTools(Student student, String[] toolsToRemove){
		boolean result = true; 
		if(toolsToRemove != null){
			student.removeTools(toolsToRemove);
		}
		if(result) {
			Registry.studentFinder().put(student);
		}
	}
}

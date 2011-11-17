package projectatlast.student;

import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import com.googlecode.objectify.Key;

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
		return Registry.activityFinder().getActivity(student.getActivity());
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
		if (activity == null) {
			student.setActivity(null);
		} else {
			Key<Activity> activityKey = Registry.activityFinder().getKey(
					activity);
			if (activityKey == null)
				return false;
			student.setActivity(activityKey);
		}
		Registry.studentFinder().put(student);
		return true;
	}

}

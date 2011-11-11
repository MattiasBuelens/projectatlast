package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.Activity;

import java.util.Collection;

import com.googlecode.objectify.Key;


public class StudentController {

	public static Activity getCurrentActivity(Student student) {
		return Registry.activityFinder().getActivity(student.getActivity());
	}
	
	public static boolean setCurrentActivity(Student student, Activity activity) {
		if(activity == null) {
			student.setActivity(null);
		} else {
			Key<Activity> activityKey = Registry.activityFinder().getKey(activity);
			if(activityKey == null) return false;
			student.setActivity(activityKey);
		}
		Registry.studentFinder().put(student);
		return true;
	}

	/**
	 * Sets the courses on a student.
	 * 
	 * @param student
	 *            the student
	 * @param courses
	 *            the new courses
	 * @return true if success, false otherwise.
	 */
	public boolean setCourses(Student student, Collection<Course> courses) {
		if (student == null || courses == null)
			return false;
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}

}

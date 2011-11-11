package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.List;

public class ActivityController {

	public static boolean startActivity(Activity activity) {
		activity.start();
		Registry.activityFinder().put(activity);
		return true;
	}

	public static boolean stopActivity(Activity activity) {
		activity.stop();
		Registry.activityFinder().put(activity);
		return true;
	}

	public static Activity getCurrentFromStudent(Student student) {
		if (student == null)
			return null;
		return Registry.activityFinder().getActivity(student.getActivity());
	}
	
	public static List<Activity> getAllFromStudent(Student student) {
		return Registry.activityFinder().findByStudent(student);
	}

}

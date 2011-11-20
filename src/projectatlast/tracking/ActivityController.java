package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.List;

public class ActivityController {

	public static boolean startActivity(Activity activity) {
		if (activity == null)
			return false;
		activity.start();
		return Registry.activityFinder().put(activity);
	}

	public static boolean stopActivity(Activity activity) {
		if (activity == null)
			return false;
		activity.stop();
		return Registry.activityFinder().put(activity);
	}

	public static Activity getCurrentFromStudent(Student student) {
		if (student == null)
			return null;
		return Registry.activityFinder().getActivity(student.getActivity());
	}

	public static List<Activity> getAllFromStudent(Student student) {
		if (student == null)
			return null;
		return Registry.activityFinder().findByStudent(student);
	}

	public static boolean removeActivity(Activity activity) {
		if (activity == null)
			return false;
		return Registry.activityFinder().remove(activity);
	}
}

package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class ActivityController {

	public static boolean startActivity(Activity activity) {
		if (activity == null)
			return false;
		// Start activity and put
		activity.start();
		return Registry.activityFinder().put(activity);
	}
	
	//Stop
	public static boolean stopActivity(Activity activity) {
		if (activity == null)
			return false;
		// Stop activity and put
		activity.stop();
		boolean result = Registry.activityFinder().put(activity);
		// Put activity slices
		if (result) {
			result = putSlices(activity);
		}
		return result;
	}
	
	public static boolean updateStudyActivity(StudyActivity studyActivity, String social, Collection<String> tools) {
		if(studyActivity == null)
			return false;
		studyActivity.setSocial(social);
		studyActivity.setTools(tools);
		return stopActivity(studyActivity);
	}

	public static boolean updateStudyActivity(StudyActivity studyActivity, String social, String[] tools) {
		List<String> toolsList;
		if(tools != null) {
			toolsList = Arrays.asList(tools);
		} else {
			toolsList = Collections.emptyList();
		}
		return updateStudyActivity(studyActivity, social, toolsList);
	}
	
	public static boolean updateStudyActivity(long activityId, String social, String[] tools) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if(activity instanceof StudyActivity) {
			return updateStudyActivity((StudyActivity)activity, social, tools);
		}
		return false;
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

	public static boolean putSlices(Activity activity) {
		Key<Activity> key = Registry.activityFinder().getKey(activity);
		boolean result = false;
		if (key == null)
			return false;

		// Collect new slices
		List<ActivitySlice> slices = ActivitySlice.build(activity);

		// Begin transaction
		Objectify ofy = Registry.dao().beginTransaction();

		try {
			// Delete previous slices
			ofy.delete(ofy.query(ActivitySlice.class).ancestor(key)
					.fetchKeys());
			// Put new slices
			ofy.put(slices);
			ofy.getTxn().commit();
			result = true;
		} finally {
			// Roll back on failure
			if (ofy.getTxn().isActive()) {
				ofy.getTxn().rollback();
			}
		}

		return result;
	}
}

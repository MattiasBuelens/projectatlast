package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.*;

public class ActivityController {

	public static boolean startActivity(Activity activity) {
		if (activity == null)
			return false;
		// Start activity and put
		activity.start();
		return Registry.activityFinder().put(activity);
	}

	// Stop
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

	public static List<Activity> getAllFromStudent(Student student) {
		if (student == null)
			return null;
		return Registry.activityFinder().findByStudent(student);
	}

	public static boolean removeActivity(Activity activity) {
		boolean result = true;
		result = result && (activity != null);
		result = result && Registry.activityFinder().remove(activity);
		result = result && deleteSlices(activity);
		return result;
	}
	
	public static boolean removeActivity(long activityId) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if (activity == null) {
			return false;
		}
		return removeActivity(activity);
	}

	public static boolean verifyOwner(Activity activity, Student student) {
		return activity.getStudent().equals(student);
	}

	public static boolean verifyOwner(long activityId, Student student) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if (activity == null) {
			return false;
		}
		return verifyOwner(activity, student);
	}

	public static boolean updateActivity(Activity activity, long moodInterest,
			long moodComprehension) {
		if (activity == null)
			return false;
		Mood mood = new Mood(moodInterest, moodComprehension);
		activity.setMood(mood);
		return put(activity);
	}

	/**
	 * Methodes voor StudyActivities
	 * 
	 * @param studyActivity
	 * @param social
	 * @param tools
	 * @return
	 */
	public static boolean updateStudyActivity(StudyActivity studyActivity,
			long pages, String social, List<String> tools, String location,
			long moodInterest, long moodComprehension) {
		if (studyActivity == null)
			return false;
		if (social != null)
			studyActivity.setSocial(social);
		if (tools != null)
			studyActivity.setTools(tools);
		if (location != null) {
			studyActivity.setLocation(location);
		}
		if (studyActivity.getType().equalsIgnoreCase("study")) {
			studyActivity.setPages(pages);
		}
		return updateActivity(studyActivity, moodInterest, moodComprehension);
	}

	public static boolean updateStudyActivity(StudyActivity studyActivity,
			long pages, String social, String[] tools, String location,
			long moodInterest, long moodComprehension) {
		List<String> toolsList = new ArrayList<String>();
		if (tools != null) {
			toolsList.addAll(Arrays.asList(tools));
		}
		return updateStudyActivity(studyActivity,
				pages,
				social,
				toolsList,
				location,
				moodInterest,
				moodComprehension);
	}

	public static boolean updateStudyActivity(long activityId, long pages,
			String social, String[] tools, String location, long moodInterest,
			long moodComprehension) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if (activity instanceof StudyActivity) {
			return updateStudyActivity((StudyActivity) activity,
					pages,
					social,
					tools,
					location,
					moodInterest,
					moodComprehension);
		}
		return false;
	}

	// TODO Make this protected after testing
	// Currently used by RandomActivityTestServlet
	public static boolean put(Activity activity) {
		// Put activity
		boolean result = Registry.activityFinder().put(activity);
		// Put activity slices
		if (result) {
			result = putSlices(activity);
		}
		return result;
	}

	protected static boolean putSlices(Activity activity) {
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
			ofy.delete(ofy.query(ActivitySlice.class).ancestor(key).fetchKeys());
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

	protected static boolean deleteSlices(Key<Activity> key) {
		Objectify ofy = Registry.dao().ofy();
		Query<ActivitySlice> q = ofy.query(ActivitySlice.class).ancestor(key);
		ofy.delete(q.fetchKeys());
		return true;
	}

	protected static boolean deleteSlices(Activity activity) {
		Key<Activity> key = Registry.activityFinder().getKey(activity);
		if (key == null)
			return false;
		return deleteSlices(key);
	}

	public static Map<String, String> getStudyTypes() {
		return StudyActivity.getTypes();
	}
}

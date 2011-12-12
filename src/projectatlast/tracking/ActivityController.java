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
		return put(activity);
	}

	public static boolean stopActivity(Activity activity) {
		if (activity == null)
			return false;
		// Stop activity and put
		activity.stop();
		return put(true, activity);
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
		result = result && Registry.activityFinder().removeSlices(activity);
		return result;
	}

	public static boolean removeActivity(long activityId) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if (activity == null) {
			return false;
		}
		return removeActivity(activity);
	}

	public static boolean removeActivity(long activityId, Student student) {
		Activity activity = Registry.activityFinder().getActivity(activityId);
		if (activity == null) {
			return false;
		}
		if (!verifyOwner(activity, student))
			return false;
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

	public static boolean put(Collection<Activity> activities) {
		return Registry.activityFinder().put(activities);
	}

	public static boolean put(Activity... activities) {
		return Registry.activityFinder().put(activities);
	}

	public static boolean put(boolean newSlices, Collection<Activity> activities) {
		return Registry.activityFinder().put(newSlices, activities);
	}

	public static boolean put(boolean newSlices, Activity... activities) {
		return Registry.activityFinder().put(newSlices, activities);
	}

	/*
	 * Study activities
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
		Mood mood = new Mood(moodInterest, moodComprehension);
		studyActivity.setMood(mood);
		return put(studyActivity);
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

	public static Map<String, String> getStudyTypes() {
		return StudyActivity.getTypes();
	}
}

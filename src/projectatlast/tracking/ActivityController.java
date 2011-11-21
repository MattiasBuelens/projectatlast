package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

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
	
	

	public static boolean putSlices(Activity activity) {
		Key<Activity> key = Registry.activityFinder().getKey(activity);
		boolean success = false;
		if(key == null) return false;

		// Collect new slices
		Set<ActivitySlice> slices = ActivitySlice.build(activity);
		
		// Begin transaction
		Objectify otxn = Registry.dao().beginTransaction();


		try
		{
			// Delete previous slices
			otxn.delete(otxn.query(ActivitySlice.class).ancestor(key).fetchKeys());
			// Put new slices
			otxn.put(slices);
			otxn.getTxn().commit();
			success = true;
		}
		finally
		{
			// Rollback on failure
		    if (otxn.getTxn().isActive()) {
		    	otxn.getTxn().rollback();
		    }
		}
		
		return success;
	}
}

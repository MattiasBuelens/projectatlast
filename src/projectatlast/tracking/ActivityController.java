package projectatlast.tracking;

import projectatlast.data.Registry;

public class ActivityController {

	public static boolean startFreeTimeActivity(FreeTimeActivity activity) {
		
		Registry.activityFinder().put(activity);
		activity.start();
		
		return true;
	}

}

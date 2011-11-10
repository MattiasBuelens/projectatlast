package projectatlast.data;

import projectatlast.tracking.Activity;
import projectatlast.tracking.FreeTimeActivity;

import java.util.List;

import com.googlecode.objectify.Key;

public class ActivityFinder extends Finder {

	public ActivityFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	
	}
	
	public List<Activity> getAllFromStudent(Object student) {
		return null;
	}

	public void put(Activity activity) {
		dao.begin().put(activity);		
	}
	
	public Key<Activity> getKey(Activity activity){
		return dao.key(activity);
	}

}

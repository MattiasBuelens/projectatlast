package projectatlast.tracking;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.Collections;
import java.util.List;

import com.googlecode.objectify.*;

public class ActivityFinder extends Finder {

	public ActivityFinder(DAO dao) {
		super(dao);
	}

	public Activity getActivity(Key<Activity> key) {
		if (key == null)
			return null;
		try {
			return dao.begin().get(key);
		} catch (NotFoundException e) {
			return null;
		}
	}
	
	public Activity getActivity(long activityId) {
		return getActivity(getKey(activityId));
	}

	public Key<Activity> getKey(Activity activity) {
		if (activity == null)
			return null;
		return dao.key(activity);
	}

	public Key<Activity> getKey(long activityId) {
		if (activityId <= 0)
			return null;
		return new Key<Activity>(Activity.class, activityId);
	}

	public List<Activity> findByStudent(Student student) {
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Activity> q = dao.ofy().query(Activity.class)
				.filter("student =", studentKey).order("-startDate");
		return q.list();
	}

	public boolean put(Activity activity) {
		if (activity == null)
			return false;
		dao.ofy().put(activity);
		return true;
	}
	
	public boolean remove(Activity activity) {
		if(activity == null)
			return false;
		dao.ofy().delete(activity);
		return true;
	}

}

package projectatlast.tracking;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class ActivityFinder extends Finder {

	public ActivityFinder(DAO dao) {
		super(dao);
	}

	public Activity getActivity(Key<Activity> key) {
		if (key == null)
			return null;
		return dao.begin().get(key);
	}

	public Key<Activity> getKey(Activity activity) {
		if (activity == null)
			return null;
		return dao.key(activity);
	}

	public List<Activity> findByStudent(Student student) {
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return null;
		Query<Activity> q = dao.ofy().query(Activity.class)
				.filter("student =", studentKey).order("startDate");
		return q.list();
	}

	public void put(Activity activity) {
		dao.begin().put(activity);
	}

}

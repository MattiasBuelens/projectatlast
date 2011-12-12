package projectatlast.tracking;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.*;

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

	public boolean put(boolean newSlices, Collection<Activity> activities) {
		if (activities == null || activities.isEmpty())
			return false;
		// Put activities
		Map<Key<Activity>, Activity> results = dao.ofy().put(activities);
		boolean result = !results.isEmpty();
		// Put activity slices if requested
		if (result && newSlices) {
			result = putSlices(activities);
		}
		return result;
	}

	public boolean put(boolean newSlices, Activity... activities) {
		return put(newSlices, Arrays.asList(activities));
	}

	public boolean put(Collection<Activity> activities) {
		return put(false, activities);
	}

	public boolean put(Activity... activities) {
		return put(false, activities);
	}

	protected boolean putSlices(Collection<Activity> activities) {
		Objectify ofy = dao.ofy();
		boolean result = false;

		if (activities == null || activities.isEmpty()) {
			return false;
		}

		// Get old slices and build new ones
		List<Key<ActivitySlice>> oldSlices = new ArrayList<Key<ActivitySlice>>(
				activities.size());
		List<ActivitySlice> newSlices = new ArrayList<ActivitySlice>();
		for (Activity activity : activities) {
			oldSlices.addAll(ofy.query(ActivitySlice.class).ancestor(activity)
					.listKeys());
			newSlices.addAll(ActivitySlice.build(activity));
		}

		// Delete old slices
		ofy.delete(oldSlices);
		// Put new slices
		ofy.put(newSlices);

		return result;
	}

	protected boolean putSlices(Activity... activities) {
		return putSlices(Arrays.asList(activities));
	}

	public boolean remove(Collection<Activity> activities) {
		if (activities == null || activities.isEmpty())
			return false;
		dao.ofy().delete(activities);
		return true;
	}

	public boolean remove(Activity... activities) {
		return remove(Arrays.asList(activities));
	}

	protected boolean removeSlices(Key<Activity> key) {
		Objectify ofy = dao.ofy();
		Query<ActivitySlice> q = ofy.query(ActivitySlice.class).ancestor(key);
		ofy.delete(q.fetchKeys());
		return true;
	}

	protected boolean removeSlices(Activity activity) {
		Key<Activity> key = getKey(activity);
		if (key == null)
			return false;
		return removeSlices(key);
	}
}

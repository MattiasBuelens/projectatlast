package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class ActivitySlice {
	public static final int SLICE_FIELD = Calendar.HOUR_OF_DAY;
	public static final int SLICE_STEP = 1;

	@Id Long id;
	@Parent Key<Activity> activity;
	Key<Student> student;

	Date date;
	int dayOfWeek;
	int hourOfDay;
	long duration;

	protected ActivitySlice() {}

	public ActivitySlice(Key<Activity> activity, Key<Student> student,
			Date date, long duration) {
		this.activity = activity;
		this.student = student;
		setDate(date);
		setDuration(duration);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return Registry.studentFinder().getStudent(student);
	}

	public Key<Activity> getActivity() {
		return activity;
	}

	public void setActivity(Key<Activity> activity) {
		this.activity = activity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		updateDate();
	}

	protected void updateDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public static List<ActivitySlice> build(Activity activity) {
		Key<Activity> key = Registry.activityFinder().getKey(activity);
		List<ActivitySlice> slices = new ArrayList<ActivitySlice>();
		if (key == null)
			return slices;

		Key<Student> student = Registry.studentFinder()
				.getKey(activity.getStudent());
		Date startDate = activity.getStart(), endDate = activity.getEnd();
		Date prevDate = startDate, nextDate = startDate;
		long remainingDuration = activity.getDuration();
		long sliceDuration;

		// Create calendar with start date
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		// Clean time fields
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// As long as there's a duration to be sliced
		while (remainingDuration > 0) {
			// Step
			prevDate = nextDate;
			cal.add(SLICE_FIELD, SLICE_STEP);
			nextDate = cal.getTime();
			if (nextDate.after(endDate)) {
				nextDate = endDate;
			}
			// Get slice duration and substract from remaining
			sliceDuration = nextDate.getTime() - prevDate.getTime();
			remainingDuration -= sliceDuration;
			// Create slice
			slices.add(new ActivitySlice(key, student, prevDate, sliceDuration));
		}
		// Add null slice at end
		slices.add(new ActivitySlice(key, student, endDate, 0));

		return slices;
	}

}

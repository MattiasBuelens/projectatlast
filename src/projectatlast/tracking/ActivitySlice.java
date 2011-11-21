package projectatlast.tracking;

import projectatlast.data.Registry;

import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class ActivitySlice {

	@Id Long id;
	@Parent Key<Activity> activity;
	public static final int SLICE_FIELD = Calendar.HOUR_OF_DAY;
	public static final int SLICE_STEP = 1;

	Date date;
	int dayOfWeek;
	int hourOfDay;
	long duration;


	protected ActivitySlice() {	}

	
	public ActivitySlice(Key<Activity> parent, Date date, long duration) {
		activity = parent;
		setDate(date);
		setDuration(duration);
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public static Set<ActivitySlice> build(Activity activity) {
		Key<Activity> key = Registry.activityFinder().getKey(activity);
		Set<ActivitySlice> slices = new HashSet<ActivitySlice>();
		if(key == null) return slices;

		Date startDate = activity.getStart();
		Date endDate = activity.getEnd();
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
		// Step once
		cal.add(SLICE_FIELD, SLICE_STEP);
		nextDate = cal.getTime();

		// As long as there's a duration to be sliced
		while(remainingDuration > 0 && prevDate.compareTo(endDate) < 0) {
			// Get slice duration and substract from remaining
			sliceDuration = nextDate.getTime() - prevDate.getTime();
			remainingDuration -= sliceDuration;
			// Create slice
			slices.add(new ActivitySlice(key, prevDate, sliceDuration));
			// Step
			cal.add(SLICE_FIELD, SLICE_STEP);
			prevDate = nextDate;
			nextDate = cal.getTime();
			// Cap at end date
			if(nextDate.compareTo(endDate) > 0) {
				nextDate = endDate;
			}
		}
		// Add null slice at end
		slices.add(new ActivitySlice(key, endDate, 0));
		
		return slices;
	}

}



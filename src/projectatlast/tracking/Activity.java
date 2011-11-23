package projectatlast.tracking;

import projectatlast.data.JSONable;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public abstract class Activity implements JSONable {

	@Id Long id;

	Key<Student> student;
	Date startDate;
	Date endDate;
	long duration;
	String type;
	@Embedded Mood mood;
	
	protected Activity() { }

	public Activity(Student student, String type) {
		setStudent(student);
		setType(type);
	}
	
	public void start() {
		setStart(Calendar.getInstance().getTime());
	}
	
	public void stop(){
		setEnd(Calendar.getInstance().getTime());
	}

	public Date getStart() {
		return startDate;
	}

	public void setStart(Date startDate) {
		if(startDate != null) {
			this.startDate = startDate;
		}
		updateDuration();
	}
	
	public Date getEnd() {
		return endDate;
	}
	
	public void setEnd(Date endDate) {
		if(endDate != null) {
			this.endDate = endDate;
		}
		updateDuration();
	}

	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
		updateDuration(duration);
	}

	public Mood getMood() {
		return mood;
	}
	
	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Student getStudent() {
		return Registry.studentFinder().getStudent(student);
	}
	
	protected void setStudent(Key<Student> student) {
		this.student = student;
	}
	
	protected void setStudent(Student student) {
		setStudent(Registry.studentFinder().getKey(student));
	}
	
	protected void updateDuration() {
		if(startDate != null && endDate != null) {
			this.duration = endDate.getTime() - startDate.getTime();
		}
	}
	
	protected void updateDuration(long duration) {
		if(startDate != null) {
			endDate = new Date(startDate.getTime() + duration);
		} else if(endDate != null) {
			startDate = new Date(endDate.getTime() - duration);
		}
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		//json.put("id", id);
		json.put("student", student == null ? null : student.getId());
		json.put("startDate", startDate.getTime());
		json.put("endDate", endDate.getTime());
		json.put("duration", duration);
		json.put("mood", mood == null ? null : mood.toJSON());
		return json;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[" + id + "]";
	}
	
	/**
	 * Retrieves the associated <code>Key&lt;Activity&gt;</code> from
	 * an entity object.
	 * 
	 * <p><code>Activity</code> objects simply return their own key.
	 * Other entities might return the key of their parent or a key
	 * stored in some property.</p>
	 *  
	 * @param key - the key of the entity.
	 * @param obj - the entity object.
	 * @return the associated activity key.
	 */
	/* TODO: Find a better way to do this */
	@SuppressWarnings("unchecked")
	public static Key<Activity> keyFromObject(Key<?> key, Object obj) {
		if(obj instanceof Activity) {
			return (Key<Activity>)key;
		} else if(obj instanceof ActivitySlice) {
			return ((Key<ActivitySlice>)key).getParent();
		}
		return null;
	}

	/**
	 * Get the available type values for this activity.
	 * 
	 * @return list of available types.
	 */
	public List<String> getTypes() {
		return Collections.emptyList();
	}
	
	/**
	 * Use information from the slice to update
	 * dates and duration
	 * @param slice
	 */
	public void addSlice(ActivitySlice slice){
		Date sliceDate = slice.getDate();
		
		/*
		 * Change date range
		 * If the sliceDate is before the current startdate of the
		 * activity, then sliceDate is the new startdate.
		 * 
		 * The same is valid for enddate, only here slicedate must be after enddate
		 * 
		 */
		
		System.out.println("88888");
	

		if(sliceDate.getTime()<getStart().getTime()){
			System.out.println("setFFFFf");
			setStart(sliceDate);
		}else if(sliceDate.getTime()>getEnd().getTime()){
			setEnd(sliceDate);
		}
		
		//update the duration
		updateDuration();
	}
	
	

}

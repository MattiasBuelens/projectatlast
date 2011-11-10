package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public abstract class Activity {

	@Id Long id;

	Date startDate;
	Date endDate;
	long duration;
	@Embedded Mood mood;
	String type;
	Key<Student> student;
	
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
		setDates(startDate, this.endDate);
	}
	
	public Date getEnd() {
		return endDate;
	}
	
	public void setEnd(Date endDate) {
		setDates(this.startDate, endDate);
	}

	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		setDates(this.startDate, duration);
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
		setStudent(Registry.dao().key(student));
	}
	
	protected void setDates(Date startDate, Date endDate) {
		this.startDate = startDate;
		if(endDate != null) {
			this.endDate = endDate;
			this.duration = endDate.getTime() - startDate.getTime();
		}
	}
	
	protected void setDates(Date startDate, long duration) {
		this.startDate = startDate;
		this.duration = duration;
		this.endDate = new Date(startDate.getTime() + duration);
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
}

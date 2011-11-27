package projectatlast.tracking;

import projectatlast.data.JSONable;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import javax.persistence.*;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public abstract class Activity implements JSONable, Cloneable {

	@Id Long id;

	Key<Student> student;
	Date startDate;
	Date endDate;
	long duration;
	String type;
	@Embedded Mood mood;

	protected Activity() {}

	public Activity(Student student, String type) {
		setStudent(student);
		setType(type);
	}

	public void start() {
		setStart(Calendar.getInstance().getTime());
	}

	public void stop() {
		setEnd(Calendar.getInstance().getTime());
	}

	public long getId() {
		return id;
	}

	public Date getStart() {
		return startDate;
	}

	public void setStart(Date startDate) {
		if (startDate != null) {
			this.startDate = startDate;
		}
		updateDuration();
	}

	public Date getEnd() {
		return endDate;
	}

	public void setEnd(Date endDate) {
		if (endDate != null) {
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
		if (startDate != null && endDate != null) {
			this.duration = endDate.getTime() - startDate.getTime();
		}
	}

	protected void updateDuration(long duration) {
		if (startDate != null) {
			endDate = new Date(startDate.getTime() + duration);
		} else if (endDate != null) {
			startDate = new Date(endDate.getTime() - duration);
		}
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", id);
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

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
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
	 * Add a slice to this activity.
	 * 
	 * <p>There are two valid ways to add a slice:
	 * <ul>
	 * <li>If the slice starts when this activity ends, the slice will be
	 * appended.</li>
	 * <li>If the slice ends when this activity starts, the slice will be
	 * prepended.</li>
	 * </ul>
	 * In any other case, the slice will be considered invalid for addition.
	 * 
	 * @param slice
	 *            The slice to add.
	 * @return True if the slice was added, false if it was invalid;
	 */
	public boolean addSlice(ActivitySlice slice) {
		if (slice == null)
			return false;
		boolean result = false;
		Date sliceStart = slice.getDate();
		Date sliceEnd = new Date(sliceStart.getTime() + slice.getDuration());

		if (getEnd().equals(sliceStart)) {
			// Append
			endDate = sliceEnd;
			updateDuration();
			result = true;
		} else if (getStart().equals(sliceEnd)) {
			// Prepend
			startDate = sliceStart;
			updateDuration();
			result = true;
		}

		return result;
	}

}

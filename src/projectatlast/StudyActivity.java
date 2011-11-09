package projectatlast;

import projectatlast.data.Registry;
import projectatlast.student.Course;

import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;

	@Transient Course courseObject;

	protected StudyActivity() { }

	public StudyActivity(Key<Course> course) {
		this();
		this.course = course;
	}

	public Course getCourse() {
		if(courseObject == null) {
			courseObject = Registry.courseFinder().getCourse(course);
		}
		return courseObject;
	}

	public String toString() {
		return getCourse().getName();
	}

	@PostLoad
	@SuppressWarnings("unused")
	private void clearTransients() {
		courseObject = null;
	}
}

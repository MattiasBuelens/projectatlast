package projectatlast;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Course course;

	protected StudyActivity() { }

	public StudyActivity(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return this.course;
	}

	public String toString() {
		return course.getName();
	}

}

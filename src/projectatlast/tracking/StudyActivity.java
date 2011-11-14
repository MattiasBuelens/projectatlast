package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.Collections;
import java.util.List;

import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;

	protected StudyActivity() { }

	public StudyActivity(Student student, String type, Key<Course> coursekey) {
		super(student, type);
		this.course = coursekey;
	}
	
	public StudyActivity(Student student, String type, Course course) {
		super(student, type);
		this.course = Registry.courseFinder().getKey(course);
	}

	public Course getCourse() {
		return Registry.courseFinder().getCourse(course);
	}

	public String toString() {
		return getCourse().getName();
	}

	@Override
	public List<String> getTypes() {
		return Collections.emptyList();
	}
}

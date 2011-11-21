package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.Collections;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;

	protected StudyActivity() { }

	public StudyActivity(Student student, String type, Key<Course> courseKey) {
		super(student, type);
		setCourse(courseKey);
	}
	
	public StudyActivity(Student student, String type, Course course) {
		super(student, type);
		setCourse(course);
	}
	
	public StudyActivity(Student student, String type, String courseId) {
		super(student, type);
		setCourse(courseId);
	}

	public Course getCourse() {
		return Registry.courseFinder().getCourse(course);
	}
	
	public void setCourse(Key<Course> courseKey) {
		this.course = courseKey;
	}
	
	public void setCourse(Course course) {
		setCourse(Registry.courseFinder().getKey(course));
	}
	
	public void setCourse(String courseId) {
		setCourse(Registry.courseFinder().getKey(courseId));
	}

	@Override
	public String toString() {
		return super.toString(); // + "(" + getCourse().getName() + ")";
	}

	@Override
	public List<String> getTypes() {
		return Collections.emptyList();
	}
}

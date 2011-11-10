package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.Course;
import projectatlast.student.Student;

import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;

	@Transient Course courseObject;

	protected StudyActivity(){}

	public StudyActivity(Student student, String type,Course course) {
		super(student, type);
		this.course=Registry.dao().key(course);
		// TODO Auto-generated constructor stub
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
	
	
	public static String[] getTypes(){
		
		return null;
	}
}

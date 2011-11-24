package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;
	String social;
	ArrayList<String> tools;

	protected StudyActivity() { }

	public StudyActivity(Student student, String type, Key<Course> courseKey) {
		super(student, type);
		setCourse(courseKey);
		tools = new ArrayList<String>();
	}

	public StudyActivity(Student student, String type, Course course) {
		super(student, type);
		setCourse(course);
		tools = new ArrayList<String>();
	}

	public StudyActivity(Student student, String type, String courseId) {
		super(student, type);
		setCourse(courseId);
		tools = new ArrayList<String>();
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

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}
	public void setTools(String[] tools){
		for(String tool : tools)
		{
			this.tools.add(tool);
		}
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

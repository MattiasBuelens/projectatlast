package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import com.google.appengine.repackaged.org.json.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class StudyActivity extends Activity {

	Key<Course> course;
	String social;
	List<String> tools;

	protected StudyActivity() { }
	
	protected StudyActivity(Student student, String type) {
		super(student, type);
		setSocial("Alone");
	}

	public StudyActivity(Student student, String type, Key<Course> courseKey) {
		this(student, type);
		setCourse(courseKey);
	}

	public StudyActivity(Student student, String type, Course course) {
		this(student, type);
		setCourse(course);
	}

	public StudyActivity(Student student, String type, String courseId) {
		this(student, type);
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

	public String getSocial() {
		return social;
	}

	public void setSocial(String social) {
		this.social = social;
	}
	
	public List<String> getTools() {
		if (tools == null)
			tools = Collections.emptyList();
		return tools;
	}

	public void setTools(Collection<String> tools) {
		if (this.tools == null)
			this.tools = new ArrayList<String>();
		this.tools.addAll(tools);
	}

	public void addTool(String tool) {
		if (this.tools == null)
			this.tools = new ArrayList<String>();
		tools.add(tool);
	}
	
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = super.toJSON();

		Course course = getCourse();
		json.put("course", course == null ? null : course.toJSON());
		json.put("social", getSocial());
		json.put("tools", new JSONArray(getTools()));
		return json;
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

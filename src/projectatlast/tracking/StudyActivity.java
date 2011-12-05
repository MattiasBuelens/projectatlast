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
	String location;


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
			tools = new ArrayList<String>();
		return tools;
	}

	public void setTools(Collection<String> newTools) {
		getTools().clear();
		for(String tool : newTools){
			if(!tool.isEmpty()){	
				tools.add(tool);
			}
		}
	}
	
	public String getLocation() {
			return location;
	}

	
	public void setLocation(String location) {
		this.location = location;
	}

	public boolean addTool(String tool) {
		List<String> tools = getTools();
		return !tools.contains(tool) && tools.add(tool);
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

	public static Map<String, String> getTypes() {
		Map<String, String> types = new LinkedHashMap<String, String>();
		types.put("lecture", "Lecture");
		types.put("practice", "Practice");
		types.put("study", "Study");
		types.put("exercises", "Exercises");
		return types;
	}
	
	// TODO What the fuck is this?
	public String getUsedTools(){
		String result = "";
		if (tools == null)
			tools = new ArrayList<String>();
		if(tools.size()>0){
		for(String tool:tools){
			result +=tool.toString()+", ";
		}
		}else{
			result = "Nothing";
		}
		return result;
	}
	
}

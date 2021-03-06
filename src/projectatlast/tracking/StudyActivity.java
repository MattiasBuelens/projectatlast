package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

import javax.persistence.Embedded;

import com.google.appengine.repackaged.org.json.*;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

@Subclass
@Unindexed
public class StudyActivity extends Activity {

	@Indexed Key<Course> course;
	String social;
	List<String> tools;
	String location;
	Long pages = null;
	Double pagesPerHour = null;
	@Embedded Mood mood = new Mood();

	protected StudyActivity() {}

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

	public Mood getMood() {
		if (mood == null)
			mood = new Mood();
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
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
		for (String tool : newTools) {
			if (tool != null && !tool.isEmpty()) {
				tools.add(tool);
			}
		}
	}

	public boolean addTool(String tool) {
		List<String> tools = getTools();
		return tool != null && !tool.isEmpty() && !tools.contains(tool)
				&& tools.add(tool);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getPages() {
		if (getPagesPerHour() == null)
			return (long) 0;
		double pages = (getDuration() * getPagesPerHour()) / (60 * 60 * 1000);
		return (long) pages;
	}

	public Double getPagesPerHour() {
		return pagesPerHour;
	}

	public void setPages(Long pages) {
		this.pages = pages;
		if (pages != null && pages != 0) {
			updatePages();
		}
	}

	protected void updatePages() {
		this.pagesPerHour = (60 * 60 * 1000 * pages) / ((double) duration);
	}

	@Override
	public String getTitle() {
		String title = "";
		Course course = getCourse();
		if (course != null) {
			title += getCourse().getName() + ": ";
		}
		title += getTypeName();
		return title;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = super.toJSON();

		Course course = getCourse();
		json.put("course", course == null ? null : course.toJSON());
		json.put("mood", mood == null ? null : mood.toJSON());
		json.put("social", getSocial());
		json.put("tools", new JSONArray(getTools()));
		return json;
	}

	public String getTypeName() {
		String type = getType();
		String typeName = getTypes().get(type);
		if (typeName == null)
			typeName = type;
		return typeName;
	}

	public static Map<String, String> getTypes() {
		Map<String, String> types = new LinkedHashMap<String, String>();
		types.put("lecture", "Lecture");
		types.put("practice", "Practice");
		types.put("study", "Study");
		types.put("exercises", "Exercises");
		return types;
	}
}

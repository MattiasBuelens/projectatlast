package projectatlast.course;

import projectatlast.data.Registry;

import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class StudyProgram {
	@Id String id;
	Set<Key<Course>> courses;
	String name;

	protected StudyProgram() { }

	public StudyProgram(String id, String name, Set<Key<Course>> courses) {
		this.courses = courses;
		this.name = name;
	}

	public StudyProgram(String id, String name, List<Course> courses) {
		this.courses = Registry.courseFinder().getKeys(courses);
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Course> getCourses() {
		return Registry.courseFinder().getCourses(courses);
	}

	public void setCourseKeys(Collection<Key<Course>> courses) {
		this.courses.clear();
		this.courses.addAll(courses);
	}

	public void setCourses(Collection<Course> courses) {
		setCourseKeys(Registry.courseFinder().getKeys(courses));
	}
}

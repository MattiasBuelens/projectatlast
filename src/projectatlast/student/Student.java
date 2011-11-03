package projectatlast.student;

import projectatlast.data.Registry;

import java.util.Collection;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;

@Entity
public class Student {
	@Id User user;
	@Indexed Collection<Key<Course>> courseKeys;

	@Transient List<Course> courses;
	
	private Student() { };
	
	public boolean addCourse(Key<Course> courseKey) {
		return courseKeys.add(courseKey);
	}
	
	public Collection<Course> getCourses(){
		return Registry.courseFinder().getCourses(this);
	}
	
	public Collection<Key<Course>> getCourseKeys() {
		return courseKeys;
	}
	
	public void setCourseKeys(Collection<Key<Course>> courseKeys) {
		this.courseKeys = courseKeys;
	}
}

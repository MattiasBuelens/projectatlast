package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

import com.googlecode.objectify.Key;

public class CourseFinder extends Finder {

	public CourseFinder(DAO dao) {
		super(dao);
	}
	
	public List<Course> getCourses(Iterable<Key<Course>> courses){
		return new ArrayList<Course>(dao.ofy().get(courses).values());
	}
	
	public Course getCourse(Key<Course> course){
		return dao.ofy().get(course);
	}

	public Course getCourse(String id) {
		return dao.ofy().get(Course.class, id);
	}
	
	public Key<Course> getKey(Course course) {
		if (course == null)
			return null;
		return dao.key(course);
	}

	public Set<Key<Course>> getKeys(Iterable<Course> courses) {
		return dao.keys(courses);
	}
	
}

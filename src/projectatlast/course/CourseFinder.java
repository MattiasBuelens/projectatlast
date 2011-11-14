package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

import com.googlecode.objectify.Key;

public class CourseFinder extends Finder {

	public CourseFinder(DAO dao) {
		super(dao);
	}
	
	public List<Course> getCourses(Collection<Key<Course>> courseKeys) {
		if(courseKeys == null || courseKeys.isEmpty()) {
			return Collections.emptyList();
		}
		return new ArrayList<Course>(dao.ofy().get(courseKeys).values());
	}
	
	public Course getCourse(Key<Course> courseKey){
		return dao.ofy().get(courseKey);
	}

	public Course getCourse(String id) {
		return dao.ofy().get(Course.class, id);
	}
	
	public Key<Course> getKey(Course course) {
		if (course == null)
			return null;
		return dao.key(course);
	}
	
	public Key<Course> getKey(String id) {
		if (id == null)
			return null;
		return new Key<Course>(Course.class, id);
	}

	public Set<Key<Course>> getKeys(Iterable<Course> courses) {
		return dao.keys(courses);
	}
	
}

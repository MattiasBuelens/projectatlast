package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;

public class CourseFinder extends Finder {

	public CourseFinder(DAO dao) {
		super(dao);
	}

	public List<Course> getCourses(Collection<Key<Course>> courseKeys) {
		if (courseKeys == null || courseKeys.isEmpty()) {
			return Collections.emptyList();
		}
		try {
			return new ArrayList<Course>(dao.ofy().get(courseKeys).values());
		} catch(NotFoundException e) {
			return Collections.emptyList();
		}
	}

	public Course getCourse(Key<Course> courseKey) {
		if (courseKey == null)
			return null;
		try {
			return dao.ofy().get(courseKey);
		} catch(NotFoundException e) {
			return null;
		}
	}

	public Course getCourse(String courseId) {
		return getCourse(getKey(courseId));
	}

	public Key<Course> getKey(Course course) {
		if (course == null)
			return null;
		return dao.key(course);
	}

	public Key<Course> getKey(String courseId) {
		if (courseId == null || courseId.isEmpty())
			return null;
		return new Key<Course>(Course.class, courseId);
	}

	public Set<Key<Course>> getKeys(Iterable<Course> courses) {
		return dao.keys(courses);
	}

}

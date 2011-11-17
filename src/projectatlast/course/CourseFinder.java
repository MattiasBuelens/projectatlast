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

	public List<Course> getCourses(Collection<Key<Course>> courseKeys, boolean sort) {
		if (courseKeys == null || courseKeys.isEmpty()) {
			return Collections.emptyList();
		}
		try {
			List<Course> courses = new ArrayList<Course>(dao.ofy().get(courseKeys).values());
			if(sort) Collections.sort(courses, new CourseNameComparator());
			return courses;
		} catch(NotFoundException e) {
			return Collections.emptyList();
		}
	}

	public List<Course> getCourses(Collection<Key<Course>> courseKeys) {
		return getCourses(courseKeys, true);
	}
	
	public List<Course> getCoursesById(Iterable<String> courseIds) {
		return getCourses(getKeysById(courseIds), true);
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
	
	public Set<Key<Course>> getKeysById(Iterable<String> courseIds) {
		Set<Key<Course>> courseKeys = new HashSet<Key<Course>>();
		for(String courseId : courseIds) {
			Key<Course> key = getKey(courseId);
			if(key != null) courseKeys.add(key);
		}
		return courseKeys;
	}

}

package projectatlast.data;

import projectatlast.student.Course;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.Key;

public class CourseFinder extends Finder {

	public CourseFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	/*public List<Course> getCourses(Student student){
		return getCourses(student.getCourseKeys());
	}*/
	
	public List<Course> getCourses(Iterable<Key<Course>> courses){
		return new ArrayList<Course>(dao.ofy().get(courses).values());
	}
	
	public Course getCourse(Key<Course> course){
		return dao.ofy().get(course);
	}

	public Course getCourse(String id) {
		return dao.ofy().get(Course.class, id);
	}
	
}

package projectatlast.data;

import projectatlast.student.Student;
import projectatlast.student.Course;

import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.Key;

public class CourseFinder extends Finder {

	public CourseFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	public Collection<Course> getCourses(Student student){
		return dao.ofy().get(student.getCourseKeys()).values();
	}
	
	public Course getCourse(Key<Course> key){
		return dao.ofy().get(key);
	}

	public Course getCourse(String id) {
		return dao.ofy().get(Course.class, id);
	}
	
}

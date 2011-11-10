package projectatlast.data;

import projectatlast.student.Course;
import projectatlast.student.Student;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

public class StudentFinder extends Finder {

	public StudentFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the student that is associated with the given User
	 * 
	 * @param user
	 * @return
	 */
	public Student getStudent(User user) {
		Student student = dao.ofy().query(Student.class).filter("user =", user)
				.get();

		return student;
	}

	/**
	 * Returns the student that is associated with the given Key.
	 * 
	 * @param key of the student
	 * @return the student
	 */
	public Student getStudent(Key<Student> key) {
		return dao.ofy().get(key);
	}

	public boolean addCourse(Student student, Course course) {
		Key<Course> courseKey = dao.key(course);
		student.addCourse(courseKey);
		put(student);
		return true;
	}

	public void put(Student student) {
		dao.ofy().put(student);
	}

	public boolean userExists(User user) {
		return getStudent(user)!=null;
	}

}

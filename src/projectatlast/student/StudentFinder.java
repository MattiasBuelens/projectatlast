package projectatlast.student;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class StudentFinder extends Finder {

	public StudentFinder(DAO dao) {
		super(dao);
	}

	/**
	 * Fetches the student associated with the given user.
	 * 
	 * @param user
	 * @return
	 */
	public Student getStudent(User user) {
		if (user == null)
			return null;
		return queryByUser(user).get();
	}

	/**
	 * Fetches the student associated with the given key.
	 * 
	 * @param key
	 *            key of the student
	 * @return the student
	 */
	public Student getStudent(Key<Student> key) {
		if (key == null)
			return null;
		return dao.ofy().get(key);
	}
	
	public Student getStudent(long studentId) {
		return getStudent(getKey(studentId));
	}
	
	public Key<Student> getKey(Student student) {
		if (student == null)
			return null;
		return dao.key(student);
	}
	
	public Key<Student> getKey(long studentId) {
		if (studentId == 0)
			return null;
		return new Key<Student>(Student.class, studentId);
	}

	/**
	 * Checks whether a student associated with the given user exists.
	 * 
	 * @param user
	 * @return true if such a student exists, false otherwise.
	 */
	public boolean userExists(User user) {
		if (user == null)
			return false;
		return queryByUser(user).count() != 0;
	}

	/**
	 * Produces a query to retrieve a student by its associated user.
	 * 
	 * @param user
	 * @return the query object
	 */
	private Query<Student> queryByUser(User user) {
		return dao.ofy().query(Student.class).filter("user =", user).limit(1);
	}

	/**
	 * Persists a student.
	 * 
	 * @param student
	 */
	public boolean put(Student student) {
		if (student == null)
			return false;
		dao.ofy().put(student);
		return true;
	}

}

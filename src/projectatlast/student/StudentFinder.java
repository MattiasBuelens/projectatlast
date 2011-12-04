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
	 * Fetch the student associated with the given user.
	 * 
	 * @param user
	 *            The user.
	 * @return The student.
	 */
	public Student getStudent(User user) {
		if (user == null)
			return null;
		return queryByUser(user).get();
	}

	/**
	 * Fetch the student associated with the given key.
	 * 
	 * @param key
	 *            The key of the student.
	 * @return The student.
	 */
	public Student getStudent(Key<Student> key) {
		if (key == null)
			return null;
		return dao.ofy().get(key);
	}

	/**
	 * Fetch the student associated with the given student ID.
	 * 
	 * @param studentId
	 *            The student's identifier.
	 * @return The student.
	 */
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
	 * Check whether a student associated with the given user exists.
	 * 
	 * @param user
	 *            The user.
	 * @return True if such a student exists, false otherwise.
	 */
	public boolean userExists(User user) {
		if (user == null)
			return false;
		return queryByUser(user).count() != 0;
	}

	/**
	 * Produce a query to retrieve a student by its associated user.
	 * 
	 * @param user
	 *            The user.
	 * @return The query object.
	 */
	private Query<Student> queryByUser(User user) {
		return dao.ofy().query(Student.class).filter("user =", user).limit(1);
	}

	/**
	 * Persist a student.
	 * 
	 * @param student
	 *            The student.
	 * @return True if successful, false otherwise.
	 */
	public boolean put(Student student) {
		if (student == null)
			return false;
		dao.ofy().put(student);
		return true;
	}

}

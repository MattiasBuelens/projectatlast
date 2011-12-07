package projectatlast.student;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
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
		return getStudent(getKey(user));
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

	public Key<Student> getKey(User user) {
		if (user == null)
			return null;
		long studentId = 0;
		Key<Student> key = null;
		// Attempt to fetch from cache
		MemcacheService memcache = dao.memcache();
		if (memcache.contains(user)) {
			studentId = (Long) memcache.get(user);
			key = getKey(studentId);
		}
		// If no value or null value in cache, run query
		if (!memcache.contains(user) || studentId == 0) {
			key = queryByUser(user).getKey();
			studentId = key.getId();
			// Store for one hour
			memcache.put(user, studentId, Expiration.byDeltaSeconds(60 * 60));
		}
		return key;
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
		return getKey(user) != null;
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

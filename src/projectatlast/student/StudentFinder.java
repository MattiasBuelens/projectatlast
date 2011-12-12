package projectatlast.student;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.*;

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
		try {
			return dao.ofy().get(key);
		} catch (NotFoundException e) {
			return null;
		}
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
		// Attempt to fetch from cache
		MemcacheService memcache = dao.memcache();
		Long studentId = (Long)memcache.get(user);
		Key<Student> key = null;

		if (studentId != null && studentId > 0) {
			key = getKey(studentId);
		} else if(!memcache.contains(user)) {
			// If no value or null value in cache, run query
			key = queryByUser(user).getKey();
			studentId = (key == null) ? null : key.getId();
			// Cache result
			memcache.put(user, studentId);
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
		// Store student in cache
		MemcacheService memcache = dao.memcache();
		memcache.put(student.getUser(), student.getId());
		return true;
	}

}

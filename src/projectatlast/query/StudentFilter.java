package projectatlast.query;

import projectatlast.data.Registry;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class StudentFilter extends Option {
	private static final long serialVersionUID = 1L;

	Key<Student> student;

	protected StudentFilter() {}

	public StudentFilter(Student student) {
		student(student);
	}

	public StudentFilter(Key<Student> student) {
		student(student);
	}

	/**
	 * Get the course.
	 * 
	 * @return the course.
	 */
	public Student student() {
		return Registry.studentFinder().getStudent(student);
	}

	/**
	 * Set the course.
	 * 
	 * @param course
	 *            The new course.
	 * @return this (for chaining)
	 */
	public StudentFilter student(Student student) {
		if (student != null) {
			Key<Student> key = Registry.studentFinder().getKey(student);
			return student(key);
		}
		return this;
	}

	/**
	 * Set the course.
	 * 
	 * @param course
	 *            The key of the new course.
	 * @return this (for chaining)
	 */
	public StudentFilter student(Key<Student> student) {
		if (student != null) {
			this.student = student;
		}
		return this;
	}

	@Override
	public Class<?> getKind() {
		return Activity.class;
	}
	
	/**
	 * Check whether this {@link StudentFilter} can be applied to a given query
	 * kind.
	 * 
	 * <p>
	 * A <code>StudentFilter</code> can be applied to {@link Activity} and
	 * {@link ActivitySlice} queries.
	 * 
	 * @param kind
	 *            The query kind.
	 * @return
	 */
	@Override
	public boolean appliesTo(Class<?> kind) {
		return ActivitySlice.class.isAssignableFrom(kind)
				|| Activity.class.isAssignableFrom(kind);
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) {
		query.filter("student", student);
	}

}

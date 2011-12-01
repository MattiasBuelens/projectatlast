package projectatlast.query;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.StudyActivity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class StudyActivityFilter extends Option {
	private static final long serialVersionUID = 1L;

	Key<Course> course;

	protected StudyActivityFilter() {}

	public StudyActivityFilter(Course course) {
		course(course);
	}

	public StudyActivityFilter(Key<Course> course) {
		course(course);
	}

	/**
	 * Set the course.
	 * 
	 * @param course
	 *            The new course.
	 * @return this (for chaining)
	 */
	public StudyActivityFilter course(Course course) {
		if (course != null) {
			Key<Course> key = Registry.courseFinder().getKey(course);
			return course(key);
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
	public StudyActivityFilter course(Key<Course> course) {
		if (course != null) {
			this.course = course;
		}
		return this;
	}

	@Override
	public Class<?> getKind() {
		return StudyActivity.class;
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) {
		query.ancestor(getKind());
	}

}

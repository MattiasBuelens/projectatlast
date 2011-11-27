package projectatlast.query;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.tracking.StudyActivity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class CourseFilter extends Option {
	private static final long serialVersionUID = 1L;

	Key<Course> course;

	protected CourseFilter() {}

	public CourseFilter(Course course) {
		course(course);
	}

	public CourseFilter(Key<Course> course) {
		course(course);
	}

	/**
	 * Get the course.
	 * 
	 * @return the course.
	 */
	public Course course() {
		return Registry.courseFinder().getCourse(course);
	}

	/**
	 * Set the course.
	 * 
	 * @param course
	 *            The new course.
	 * @return this (for chaining)
	 */
	public CourseFilter course(Course course) {
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
	public CourseFilter course(Key<Course> course) {
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
		query.filter("course", course);
	}

}

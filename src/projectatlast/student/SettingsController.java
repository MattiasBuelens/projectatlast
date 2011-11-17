package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;
import projectatlast.data.Registry;

import java.util.Collections;
import java.util.List;

public class SettingsController {
	/**
	 * Get all available study programs.
	 * 
	 * @return list of study programs
	 */
	public static List<StudyProgram> getPrograms() {
		return Registry.studyProgramFinder().getPrograms();
	}

	/**
	 * Get all courses in a given study program.
	 * 
	 * @param program
	 *            the study program
	 * @return list of courses
	 */
	public static List<Course> getProgramCourses(StudyProgram program) {
		if (program == null) {
			return Collections.emptyList();
		}
		return program.getCourses();
	}

	/**
	 * Get all courses in a given study program.
	 * 
	 * @param programId
	 *            the identifier of the study program
	 * @return list of courses
	 */
	public static List<Course> getProgramCourses(String programId) {
		return getProgramCourses(Registry.studyProgramFinder().getProgram(
				programId));
	}

	/**
	 * Get all courses of a student.
	 * 
	 * @param student
	 *            the student
	 * @return list of courses
	 */
	public static List<Course> getCourses(Student student) {
		return student.getCourses();
	}

	/**
	 * Set whether the student is configured.
	 * 
	 * @param student
	 *            the student
	 * @param isConfigured
	 *            true means the student has completed configuration, false if
	 *            he has not.
	 * @return true if successful, false otherwise.
	 */
	public static boolean setConfigured(Student student, boolean isConfigured) {
		student.setConfigured(isConfigured);
		Registry.studentFinder().put(student);
		return true;
	}

	/**
	 * Set the student as configured.
	 * 
	 * @param student
	 *            the student
	 * @return true if successful, false otherwise.
	 */
	public static boolean setConfigured(Student student) {
		return setConfigured(student, true);
	}

	/**
	 * Set the courses on a student.
	 * 
	 * @param student
	 *            the student
	 * @param courses
	 *            the new courses
	 * @return true if success, false otherwise.
	 */
	public static boolean setCourses(Student student, List<Course> courses) {
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}

	/**
	 * Set the courses on a student.
	 * 
	 * @param student
	 *            the student
	 * @param courseIds
	 *            the identifiers of the new courses
	 * @return true if success, false otherwise.
	 */
	public static boolean setCoursesById(Student student,
			Iterable<String> courseIds) {
		List<Course> courses = Registry.courseFinder()
				.getCoursesById(courseIds);
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}
}

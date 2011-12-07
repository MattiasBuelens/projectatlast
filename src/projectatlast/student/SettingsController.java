package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;
import projectatlast.data.Registry;

import java.util.Collections;
import java.util.List;

public class SettingsController {
	/**
	 * Retrieve a study program.
	 * 
	 * @param programId
	 *            the identifier of the study program
	 * @return list of courses
	 */
	public static StudyProgram getProgram(String programId) {
		return Registry.studyProgramFinder().getProgram(programId);
	}

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
		return getProgramCourses(getProgram(programId));
	}
}

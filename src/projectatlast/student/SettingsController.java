package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;
import projectatlast.data.Registry;

import java.util.Collections;
import java.util.List;

public class SettingsController {
	public static List<StudyProgram> getPrograms() {
		return Registry.studyProgramFinder().getPrograms();
	}

	public static List<Course> getProgramCourses(StudyProgram program) {
		if (program == null) {
			return Collections.emptyList();
		}
		return program.getCourses();
	}

	public static List<Course> getProgramCourses(String programId) {
		return getProgramCourses(Registry.studyProgramFinder().getProgram(
				programId));
	}

	public static List<Course> getCourses(Student student) {
		return student.getCourses();
	}

	public static boolean setCourses(Student student, List<Course> courses) {
		student.setCourses(courses);
		Registry.studentFinder().put(student);
		return true;
	}
}

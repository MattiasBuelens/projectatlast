package projectatlast.student;

import projectatlast.data.Registry;
import projectatlast.course.Course;
import projectatlast.course.StudyProgram;

import java.util.List;

public class SettingsController {
	public static List<StudyProgram> getPrograms() {
		return Registry.studyProgramFinder().getAll();
	}

	public static List<Course> getProgramCourses(StudyProgram program) {
		return program.getCourses();
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

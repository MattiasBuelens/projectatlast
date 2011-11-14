package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ConfigureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		// Get available study programs
		List<StudyProgram> programs = SettingsController.getPrograms();
		req.setAttribute("studyPrograms", programs);

		// Get current student
		Student student = AuthController.getCurrentStudent();
		req.setAttribute("student", student);

		// Get enrolled courses of current student
		List<Course> courses = SettingsController.getCourses(student);
		req.setAttribute("studentCourses", courses);
		
		req.getRequestDispatcher("/student/configure.jsp").forward(req, resp);
	}
}

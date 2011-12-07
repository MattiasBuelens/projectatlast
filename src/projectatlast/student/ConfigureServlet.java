package projectatlast.student;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ConfigureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get available study programs
		List<StudyProgram> programs = SettingsController.getPrograms();
		req.setAttribute("studyPrograms", programs);

		// Get current student
		Student student = AuthController.getCurrentStudent();
		req.setAttribute("student", student);

		// Get enrolled courses of current student
		List<Course> courses = StudentController.getCourses(student);
		req.setAttribute("studentCourses", courses);

		// Get study tools and locations
		List<String> studyTools = StudentController.getTools(student);
		req.setAttribute("studyTools", studyTools);
		List<String> studyLocations = StudentController.getLocations(student);
		req.setAttribute("studyLocations", studyLocations);

		// Get free time activity types
		List<String> freeTimeTypes = StudentController
				.getFreeTimeTypes(student);
		req.setAttribute("freeTimeTypes", freeTimeTypes);

		req.getRequestDispatcher("/student/configure.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get enrolled courses
		String[] courseIds = req.getParameterValues("courses");
		// Get settings to remove
		String[] toolsToRemove = req.getParameterValues("tools");
		String[] freeTimeTypesToRemove = req
				.getParameterValues("freeTimeTypes");
		String[] locationsToRemove = req.getParameterValues("locations");

		// Get current student
		Student student = AuthController.getCurrentStudent();

		// Set courses
		StudentController.setCoursesById(student, Arrays.asList(courseIds));

		// Set as configured
		StudentController.setConfigured(student);

		// Remove settings
		StudentController.removeTools(student, toolsToRemove);
		StudentController.removeLocations(student, locationsToRemove);
		StudentController.removeFreeTimeTypes(student, freeTimeTypesToRemove);

		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

package projectatlast.student;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class SaveConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		// Get enrolled courses
		String[] courseIds = req.getParameterValues("courses");

		// Get current student
		Student student = AuthController.getCurrentStudent();
		
		// Set courses
		SettingsController.setCoursesById(student, Arrays.asList(courseIds));
		
		// Set as configured
		SettingsController.setConfigured(student);
		
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

package projectatlast.tracking;

import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.student.StudentController;
import projectatlast.tracking.Activity;

import java.io.IOException;

import javax.servlet.http.*;

/**
 * This servlet cancels the current activity of the logged in student.
 * 
 * <p>Preconditions:</p>
 * <ul><li>The student is logged in.</li></ul>
 * 
 * @author Erik De Smedt
 * @web.servlet
 *   name=CancelCurrentActivity
 */
public class CancelCurrentActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);

		// Remove current activity in student
		StudentController.setCurrentActivity(student, null);

		// Cancel current activity
		if (activity != null) {
			ActivityController.removeActivity(activity);
		}

		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

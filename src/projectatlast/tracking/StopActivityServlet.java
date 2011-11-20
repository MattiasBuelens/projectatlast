package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class StopActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);
		
		if(activity != null) {
			// Stop current activity
			ActivityController.stopActivity(activity);
			// Remove current activity in student
			StudentController.setCurrentActivity(student, null);
		}

		// Redirect to home page
		resp.sendRedirect("/");
	}
}

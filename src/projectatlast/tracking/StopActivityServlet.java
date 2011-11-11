package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class StopActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Student student = AuthController.getCurrentStudent();

		// Stop current activity
		Activity activity = StudentController.getCurrentActivity(student);
		ActivityController.stopActivity(activity);

		// Remove current activity in student
		StudentController.setCurrentActivity(student, null);

		resp.sendRedirect("/home.jsp");

	}
}

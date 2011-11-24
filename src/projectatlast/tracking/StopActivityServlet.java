package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class StopActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get current student and his current activity
		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);

		if (activity != null) {
			// Stop current activity
			ActivityController.stopActivity(activity);
			// Remove current activity in student
			StudentController.setCurrentActivity(student, null);

			if (activity instanceof StudyActivity) {
				// Redirect to stop study activity page
				req.setAttribute("activity", (StudyActivity)activity);
				req.getRequestDispatcher("/tracking/stopStudyActivity.jsp").forward(req, resp);
				return;
			}
		}
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

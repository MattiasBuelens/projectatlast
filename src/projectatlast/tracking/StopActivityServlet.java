package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class StopActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get current student and his current activity
		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);

		// Remove current activity in student
		StudentController.setCurrentActivity(student, null);

		// Stop current activity
		if (activity != null) {
			ActivityController.stopActivity(activity);

			if (activity instanceof StudyActivity) {
				// Redirect to set study details page
				req.setAttribute("activity", activity);
				req.getRequestDispatcher("/tracking/setStudyDetails.jsp").forward(req, resp);
				return;
			}
		}
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

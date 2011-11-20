package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class StartStudyActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Retrieve request parameters
		String type = req.getParameter("type");
		String courseId = req.getParameter("course");

		Student currentStudent = AuthController.getCurrentStudent();

		// Create new study activity
		StudyActivity activity = new StudyActivity(currentStudent, type,
				courseId);

		// Start the activity
		ActivityController.startActivity(activity);

		// Set the activity on the current student
		StudentController.setCurrentActivity(currentStudent, activity);

		// Redirect to home page
		resp.sendRedirect("/");
	}
}

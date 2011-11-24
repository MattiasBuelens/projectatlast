package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class StopStudyActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);
		String social = req.getParameter("social");
		String[] tools = req.getParameterValues("tools");
		if (activity != null) {
			if (activity instanceof StudyActivity) {
				// Stop study activity
				StudyActivity studyActivity = (StudyActivity) activity;
				ActivityController.stopStudyActivity(studyActivity, social, tools);
			} else {
				// Fall back for errors
				ActivityController.stopActivity(activity);
			}
			// Remove current activity in student
			StudentController.setCurrentActivity(student, null);
		}

		// Redirect to home page
		resp.sendRedirect("/home");
	}
}
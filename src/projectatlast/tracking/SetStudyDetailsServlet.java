package projectatlast.tracking;

import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.student.StudentController;

import java.io.IOException;

import javax.servlet.http.*;

public class SetStudyDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Collect parameters
		Student student = AuthController.getCurrentStudent();
		long activityId;
		long pages = 0;
		long moodInterest = 50;
		long moodComprehension = 50;
		try {
			activityId = Long.parseLong(req.getParameter("activityId"));
		} catch (NumberFormatException e) {
			// Invalid activity identifier
			resp.sendRedirect("/home");
			return;
		}
		// Numeric fields
		try {
			pages = Long.parseLong(req.getParameter("pages"));
		} catch (NumberFormatException e) {}
		try {
			moodInterest = Long.parseLong(req.getParameter("mood-interest"));
		} catch (NumberFormatException e) {}
		try {
			moodComprehension = Long.parseLong(req
					.getParameter("mood-comprehension"));
		} catch (NumberFormatException e) {}

		String social = req.getParameter("social");
		String[] tools = req.getParameterValues("tools");
		String location = req.getParameter("location");
		if (location.equals("other")) {
			location = req.getParameter("location-other");
		}
		// Update study activity
		if (ActivityController.verifyOwner(activityId, student)) {
			ActivityController.updateStudyActivity(activityId,
					pages,
					social,
					tools,
					location,
					moodInterest,
					moodComprehension);
			StudentController.addTools(student, tools);
			StudentController.addLocation(student, location);
		}
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}
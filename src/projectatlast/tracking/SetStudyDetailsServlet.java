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
		long activityId = Long.parseLong(req.getParameter("activityId"));
		long moodInterest = Long.parseLong(req.getParameter("mood-interest"));
		long moodComprehension = Long.parseLong(req.getParameter("mood-comprehension"));
		String social = req.getParameter("social");
		String[] tools = req.getParameterValues("tools");
		String location = req.getParameter("location");
		if(location.equals("other")){
			location = req.getParameter("location-other");
		}
		// Update study activity
		if(ActivityController.verifyOwner(activityId, student)) {
			ActivityController.updateStudyActivity(activityId, social, tools, location, moodInterest, moodComprehension);
			StudentController.addTools(student, tools);
			StudentController.addLocation(student, location);
		}
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}
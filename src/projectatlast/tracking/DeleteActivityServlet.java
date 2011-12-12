package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class DeleteActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Retrieve request parameters
		long activityId = Long.parseLong(req.getParameter("id"));

		// Get current student and his current activity
		Student student = AuthController.getCurrentStudent();
		Activity currentActivity = StudentController.getCurrentActivity(student);

		// Delete the activity that belongs to the id
		boolean isRemoved = ActivityController.removeActivity(activityId);

		// Clear current activity on student if his activity was removed
		if(isRemoved && currentActivity != null && currentActivity.getId() == activityId) {
			StudentController.setCurrentActivity(student, null);
		}

		// Redirect to activities list
		resp.sendRedirect("/tracking/activities.jsp");
	}
}

package projectatlast.tracking;

import java.io.IOException;

import javax.servlet.http.*;

public class DeleteActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Retrieve request parameters
		String activityId = req.getParameter("delete-button");

		// Delete the activity that belongs to the id
		ActivityController.removeActivity(Long.parseLong(activityId));

		// Redirect to home page
		resp.sendRedirect("/tracking/activities.jsp");
	}
}

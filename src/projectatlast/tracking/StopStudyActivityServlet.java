package projectatlast.tracking;

import java.io.IOException;

import javax.servlet.http.*;

public class StopStudyActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Collect parameters
		long activityId = Long.parseLong(req.getParameter("activityId"));
		String social = req.getParameter("social");
		String[] tools = req.getParameterValues("tools");
		
		// Update study activity
		ActivityController.updateStudyActivity(activityId, social, tools);

		// Redirect to home page
		resp.sendRedirect("/home");
	}
}
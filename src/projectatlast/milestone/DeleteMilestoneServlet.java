package projectatlast.milestone;

import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class DeleteMilestoneServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get the current student
		Student student = AuthController.getCurrentStudent();

		// Get the id of the requested milestone
		Long milestoneId = null;
		try {
			milestoneId = Long.parseLong(req.getParameter("milestoneId"));
		} catch (NumberFormatException e) {
			resp.sendRedirect("/milestone/milestones.jsp");
			return;
		}

		// Get milestone out of datastore
		Milestone milestone = MilestoneController.getMilestone(milestoneId,
				student);

		if (milestone != null) {
			MilestoneController.removeMilestone(milestone);
		}

		resp.sendRedirect("/milestone/milestones.jsp");
	}
}

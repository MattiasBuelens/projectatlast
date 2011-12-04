package projectatlast.tracking;

import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.student.StudentController; 

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.*;

public class SetStudyDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Collect parameters
		Student student = AuthController.getCurrentStudent();
		long activityId = Long.parseLong(req.getParameter("activityId"));
		String social = req.getParameter("social");
		String[] tools = req.getParameterValues("tools");
		String extraTool = req.getParameter("extraTool");
		String location = req.getParameter("location");
		String extraLocation = req.getParameter("extraLocation");

		// Update study activity
		if(ActivityController.verifyOwner(activityId, student)) {
			ActivityController.updateStudyActivity(activityId, social, tools, extraTool);
			StudentController.addTool(student, extraTool);
		}
		// Redirect to home page
		resp.sendRedirect("/home");
	}
}
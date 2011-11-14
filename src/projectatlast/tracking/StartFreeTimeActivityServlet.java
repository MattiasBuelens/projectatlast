package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.http.*;

public class StartFreeTimeActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// get needed via post/get parameters
		String type = req.getParameter("type");
		Student currentStudent = AuthController.getCurrentStudent();

		// Create New FreeTimeActivity
		FreeTimeActivity activity = new FreeTimeActivity(currentStudent, type);

		// Start a new activity using ActivityController
		ActivityController.startActivity(activity);

		// Set the current activity on the currentStudent
		currentStudent.setActivity(Registry.activityFinder().getKey(activity));

		// Put the student to the datastore
		Registry.dao().ofy().put(currentStudent);

		// redirect to the home page
		resp.sendRedirect("/home.jsp");

	}
}

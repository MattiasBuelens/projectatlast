package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

public class StartFreeTimeActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Retrieve request parameters
		String type = req.getParameter("type");
		if(type.equals("other")){
			type = req.getParameter("type-other");
		}
		Student currentStudent = AuthController.getCurrentStudent();
		currentStudent.addFreeTimeType(type);

		// Create new free time activity
		FreeTimeActivity activity = new FreeTimeActivity(currentStudent, type);

		// Start activity
		ActivityController.startActivity(activity);

		// Set the activity on the current student
		StudentController.setCurrentActivity(currentStudent, activity);

		// Redirect to home page
		resp.sendRedirect("/home");
	}
}

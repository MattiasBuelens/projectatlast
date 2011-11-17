package projectatlast.tracking;

import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.student.StudentController;
import projectatlast.tracking.Activity;

import java.io.IOException;

import javax.servlet.http.*;

import com.googlecode.objectify.Key;

/**
 * 
 * @author: Erik De Smedt
 * 
 */
public class CancelCurrentActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * This servlet is called when a user wants to cancel an activity.
	 * 
	 * precondition: The user is logged in.
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);

		if (activity != null) {
			ActivityController.eraseActivity(activity);
			StudentController.setCurrentActivity(student, null);
		}

		resp.sendRedirect("/home.jsp");
	}
}

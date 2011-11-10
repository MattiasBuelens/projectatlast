package projectatlast.student;

import projectatlast.Activity;
import projectatlast.StudyActivity;
import projectatlast.data.Registry;
import projectatlast.query.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PostLoginServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		AuthController auth = new AuthController();

		// is a user logged in ? else -> login.jsp
		if (auth.activeSession()) {
			if (auth.isRegistered(auth.getCurrentUser())) {

				if (auth.getCurrentStudent().getConfigured()) {
					resp.sendRedirect("/start.jsp");
				} else {
					resp.sendRedirect("/register.jsp");
				}

			} else {
				// if not

				// register the student in the system
				Student student = new Student(auth.getCurrentUser());
				Registry.studentFinder().put(student);

				resp.sendRedirect("/register.jsp");

			}
		} else {
			// go to login.jsp
			resp.sendRedirect("/login.jsp");
		}

	}

}

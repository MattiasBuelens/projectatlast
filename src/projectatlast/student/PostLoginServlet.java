/**
 * @author thomas
 * 
 */
package projectatlast.student;

import projectatlast.Activity;
import projectatlast.StudyActivity;
import projectatlast.data.Registry;
import projectatlast.query.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

public class PostLoginServlet  extends HttpServlet  {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		AuthController auth = new AuthController();

		// is a user logged in ? else -> login.jsp
		if (auth.activeSession()) {
			System.out.println("test");
			if (auth.isRegistered(auth.getCurrentUser())) {

				System.out.println("is registered");
				if (auth.getCurrentStudent().getConfigured()) {
					System.out.println("is configured");
					resp.sendRedirect("/home.jsp");
				} else {
					
					System.out.println("not configured");
					resp.sendRedirect("/configure.jsp");
				}

			} else {
				// if not

				// register the student in the system
				Student student = new Student(auth.getCurrentUser());
				Registry.studentFinder().put(student);
				
				System.out.println("not registered");
				resp.sendRedirect("/configure.jsp");
				
			}
		} else {
			// go to login.jsp
			resp.sendRedirect("/login.jsp");
		}

	}

}

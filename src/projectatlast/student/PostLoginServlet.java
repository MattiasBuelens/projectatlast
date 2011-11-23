package projectatlast.student;

import java.io.IOException;

import javax.servlet.http.*;

public class PostLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// Is the user registered as a student?
		if (AuthController.isRegistered()) {
			// Has the student gone through configuration?
			if (AuthController.getCurrentStudent().isConfigured()) {
				resp.sendRedirect("/home");
			} else {
				resp.sendRedirect("/student/configure");
			}
		} else {
			// Register user as student
			AuthController.register();
			resp.sendRedirect("/student/configure");
		}
	}
}

package projectatlast.frontend;

import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Requires that the currently logged in student is registered as a student.
 */
public class RegistrationRequiredFilter extends FilterBase {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (shouldFilter(req)) {
			// If logged in but not registered
			if (AuthController.isLoggedIn() && !AuthController.isRegistered()) {
				// Forward to post login to register student
				req.getRequestDispatcher("/student/postlogin").forward(req, resp);
				return;
			}
		}
		// Pass on to the next filter in the chain
		chain.doFilter(req, resp);
	}
}

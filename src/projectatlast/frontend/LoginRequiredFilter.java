package projectatlast.frontend;

import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginRequiredFilter extends FilterBase {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (shouldFilter(req)) {
			// If not logged in, forward to login
			if (!AuthController.isLoggedIn()) {
				resp.sendRedirect("/student/login");
				return;
			}
		}
		// Pass on to the next filter in the chain
		chain.doFilter(req, resp);
	}
}

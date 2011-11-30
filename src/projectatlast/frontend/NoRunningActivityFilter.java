package projectatlast.frontend;

import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Requires that the current student has no running activity.
 */
public class NoRunningActivityFilter extends FilterBase {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (shouldFilter(req)) {
			// If in activity, forward to stop confirm
			if (AuthController.getCurrentStudent().isInActivity()) {
				resp.sendRedirect("/tracking/confirmStop.jsp");
				return;
			}
		}
		// Pass on to the next filter in the chain
		chain.doFilter(req, resp);
	}
}

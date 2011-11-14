package projectatlast.frontend;

import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		String jsp = "/home.jsp";
		if(!AuthController.isLoggedIn()) {
			jsp = "/authentication/login.jsp";
		}
		
		/*
		 *  NOG TE DOEN:
		 *  als not configured -> configure.
		 * 
		 */
		
		//resp.getWriter().println(jsp);
		
		// Forward request
		try {
			req.getRequestDispatcher(jsp).forward(req, resp);
		} catch (ServletException e) { }
	}

}

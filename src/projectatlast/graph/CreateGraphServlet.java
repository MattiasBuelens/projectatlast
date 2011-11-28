package projectatlast.graph;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.milestone.ComparativeOperator;
import projectatlast.milestone.Milestone;
import projectatlast.query.*;
import projectatlast.student.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.googlecode.objectify.Key;

public class CreateGraphServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		

		// Get current student
		Student student = AuthController.getCurrentStudent();
		
		String graphtype = req.getParameter("chart-type");
		String sortfield = req.getParameter("sortfield");
		String parser = req.getParameter("parser");
		String parsefield = req.getParameter("parsefield");
		String title = req.getParameter("title");

		XYGraph graph = new XYGraph(title, student, null, SortField.valueOf(sortfield), ParseField.valueOf(parsefield), Parser.valueOf(parser), GraphType.valueOf(graphtype));
		
		Key<Graph> key = Registry.graphFinder().putGraph(graph);
	
		
		Long id = Registry.dao().ofy().get(key).getId();
		
		
		resp.sendRedirect("/graph/graphs.jsp?scrollto="+id);
		
	
	}
}

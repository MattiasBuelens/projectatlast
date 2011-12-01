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

public class DeleteGraphServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		

		
		//Get the current student
		//This information will be used whether the request is valid
		Student student = AuthController.getCurrentStudent();

		


		//get the id of the requested graph
		Long graphid = Long.parseLong(req.getParameter("delid"));

		//Get graph out of datastore
		Graph graph =  Registry.graphFinder().getGraph(graphid);
		
		
		/** Check authorization**/
		if(graph.getStudent().getId()==student.getId()){
			//delete the graph
			Registry.graphFinder().remove(graph);
		}
		
		resp.sendRedirect("/graph/graphs.jsp");
	
	}
}

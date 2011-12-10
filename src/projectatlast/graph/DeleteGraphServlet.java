package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class DeleteGraphServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		

		
		//Get the current student
		//This information will be used whether the request is valid
		Student student = AuthController.getCurrentStudent();

		


		//get the id of the requested graph
		Long graphid = Long.parseLong(req.getParameter("graphId"));

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

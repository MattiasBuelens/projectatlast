package projectatlast.graph;

import projectatlast.query.QueryFactory;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class CreateGraphServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		

		// Get current student
		Student student = AuthController.getCurrentStudent();
		
		String graphtype = req.getParameter("chart-type");
		String sortfield = req.getParameter("sortfield");

		String parser = req.getParameter("parser");
		String parsefield = req.getParameter("parsefield");
		String title = req.getParameter("title");

		
		String startdate = req.getParameter("startdate");
		String stopdate = req.getParameter("stopdate");
		
		
		HashMap<String, String> optionmap = new HashMap<String, String>();
		
		optionmap.put("startdate", startdate);
		optionmap.put("enddate", stopdate);
		
		//CREATE QUERY
		QueryFactory factory = new QueryFactory();

		
		Long id = (long) 0;
		/*if(!req.getParameter("stacked").equals("true")){
			System.out.println("NOT STACKED");
		//XY GRAPH
			XYGraph graph = new XYGraph(title, student, null, GroupField.valueOf(sortfield), ParseField.valueOf(parsefield), Parser.valueOf(parser), GraphType.valueOf(graphtype));
			Key<Graph> key = Registry.graphFinder().putGraph(graph);
			id = Registry.dao().ofy().get(key).getId();
		}else{
		//STACKED GRAPH
			System.out.println("STACKED");
			String subgroup = req.getParameter("subgroup");
			
			//TEMPORARY EMPTY QUERY
			StackedGraph graph = new StackedGraph(title,student,new Query(), GroupField.valueOf(sortfield), GroupField.valueOf(subgroup), ParseField.valueOf(parsefield), Parser.valueOf(parser), GraphType.valueOf(graphtype));
			Key<Graph> key = Registry.graphFinder().putGraph(graph);
			id = Registry.dao().ofy().get(key).getId();
		
		}*/
		
		
		resp.sendRedirect("/graph/graphs.jsp?scrollto="+id);
		
	
	}
}

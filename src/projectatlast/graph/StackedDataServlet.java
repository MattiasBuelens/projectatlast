/**
 * @author Thomas Goossens
 * 
 * This servlet handles the request for StackedGraphs
 * 
 * The id of a graph is received. The servlet fetched the graph
 * and checks whether it belongs to the student requesting it.
 * 
 * It then requests StackedData from the graph.
 * 
 * The XYData (separated in X and Y arrays) are sent, together with
 * title, graphtype and possible other properties, back via JSON
 * 
 */
package projectatlast.graph;


import projectatlast.data.Registry;
import projectatlast.student.*;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.*;

public class StackedDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("application/json");

		
		//Get the current student
		//This information will be used whether the request is valid
		Student student = AuthController.getCurrentStudent();

		

		
		//different data to be sent
		List<Object> groups = null;
		Set<Object> subgroups = null;
		List<List<Long>> results = null;

		//get the id of the requested graph
		Long graphid = Long.parseLong(req.getParameter("id"));

		//Get graph out of datastore
		StackedGraph gr = (StackedGraph) Registry.graphFinder().getGraph(graphid);
		
		
		/** Check authorization**/
		if(gr.getStudent().getId()==student.getId()){
			//the requested graph belong to the logged in user
			
		//generate StackedData
		StackedData data = new StackedData(gr);

		groups = data.getGroups();
		subgroups = new HashSet<Object>(data.getSubGroups());
		results = data.getResults();
		
	
		

		// Output as JSON
		resp.setContentType("application/json");
		JSONWriter writer = new JSONWriter(resp.getWriter());
		try {
			//write groups
			writer.object().key("groups").array();
			for (Object i : groups) {
				writer.value(i);
			}
			writer.endArray();

			//write subgroups
			writer.key("subgroups").array();
			for (Object i : subgroups) {
				writer.value(i);
			}
			writer.endArray();
			
			//write y-data
			writer.key("results").array();
			for (List<Long> i : results) {
				writer.value(i);
			}
			writer.endArray();

			//write title
			writer.key("title").value(gr.getTitle());
			
			//write the graph type
			writer.key("graphtype").value(gr.getGraphType().highchartsForm());
			writer.endObject();

		} catch (JSONException e) {
			resp.getWriter().println("{\"y\":null}");
		}

		}		else{
			//The requested graph does not belong the the logged in user
		
			System.out.println("No authorization to open graph");
			resp.sendError(0,"This graph does not exist or doesn't belong to you");
		}
	}
}

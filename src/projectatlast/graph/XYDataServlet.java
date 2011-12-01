/**
 * @author Thomas Goossens
 * 
 * This servlet handles the request for XYGraphs
 * 
 * The id of a graph is received. The servlet fetched the graph
 * and checks whether it belongs to the student requesting it.
 * 
 * It then requests XYData from the graph.
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

public class XYDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("application/json");

		
		//Get the current student
		//This information will be used whether the request is valid
		Student student = AuthController.getCurrentStudent();

		

		
		List<Object> xdata = null;
		List<Long> ydata = null;

		//get the id of the requested graph
		Long graphid = Long.parseLong(req.getParameter("id"));

		//Get graph out of datastore
		XYGraph gr = (XYGraph) Registry.graphFinder().getGraph(graphid);
		
		
		/** Check authorization**/
		if(gr.getStudent().getId()==student.getId()){
			//the requested graph belong to the logged in user
			
		//generate XYData
		XYData data = gr.generateXYData();

		//Store X and Y separately
		xdata = data.getX();
		ydata = data.getY();

		

		// Output as JSON
		resp.setContentType("application/json");
		JSONWriter writer = new JSONWriter(resp.getWriter());
		try {
			//write x-data
			writer.object().key("x").array();
			for (Object i : xdata) {
				writer.value(i);
			}
			writer.endArray();

			//write y-data
			writer.key("y").array();
			for (Long i : ydata) {
				writer.value(i);
			}
			writer.endArray();

			//write title
			writer.key("title").value(gr.getTitle());
			
			//write x-axis
			String human_x = gr.getSortField().humanReadable();
			writer.key("xaxis").value(human_x);
			
			//write y-axis
			String human_y = gr.getParser().humanReadable()+" of "+gr.getParseField().humanReadable();
			writer.key("yaxis").value(human_y);
			
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

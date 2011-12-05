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

public class RequestGraphDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("application/json");

		// Get the current student
		// This information will be used whether the request is valid
		Student student = AuthController.getCurrentStudent();

		// get the id of the requested graph
		Long graphid = Long.parseLong(req.getParameter("id"));

		// Get graph out of datastore
		Graph graph = Registry.graphFinder().getGraph(graphid);

		/** Check authorization **/
		if (graph.getStudent().getId() == student.getId()) {
			// the requested graph belong to the logged in user

			// Output as JSON
			resp.setContentType("application/json");
			try {
				graph.toJSON().write(resp.getWriter());
			} catch (JSONException e) {
				resp.getWriter().println("{\"error:\":\"Invalid graph.\"}");
			}
		} else {
			resp.getWriter().println("{\"error:\":\"Invalid graph.\"}");
		}
	}
}

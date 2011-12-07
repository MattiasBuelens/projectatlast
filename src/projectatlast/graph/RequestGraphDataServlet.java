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

import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONException;

public class RequestGraphDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("application/json");

		// Get the current student
		Student student = AuthController.getCurrentStudent();
		// Get graph identifier
		Long graphId = Long.parseLong(req.getParameter("id"));

		// Retrieve graph, verifying it belongs to the student
		Graph graph = GraphController.getGraph(graphId, student);

		if (graph != null) {
			// Output as JSON
			resp.setContentType("application/json");
			try {
				graph.toJSON().write(resp.getWriter());
			} catch (JSONException e) {
				resp.getWriter().println("{\"error:\":\"JSON error while outputting graph.\"}");
			}
		} else {
			resp.getWriter().println("{\"error:\":\"Invalid graph.\"}");
		}
	}
}

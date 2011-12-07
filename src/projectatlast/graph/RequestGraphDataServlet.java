/**
 * Handles the AJAX requests for graphs.
 * 
 * The id of a graph is received. The servlet fetches the graph
 * and checks whether it belongs to the student requesting it.
 * 
 * It then obtains the graph data and outputs it as JSON.
 * 
 * @author Thomas Goossens
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

	@Override
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
				resp.getWriter().println("{\"error:\":\"JSON error.\"}");
			}
		} else {
			resp.getWriter().println("{\"error:\":\"Invalid graph.\"}");
		}
	}
}

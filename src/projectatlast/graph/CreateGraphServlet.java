package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.group.Group;
import projectatlast.group.GroupField;
import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class CreateGraphServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// get types

		String maintype = req.getParameter("maintype");
		String graphtype = req.getParameter("chart-type");

		// Get current student
		Student student = AuthController.getCurrentStudent();

		String groupfield = req.getParameter("sortfield");
		String subgroupfield = req.getParameter("subgroup");

		String parser = req.getParameter("parser");
		String parsefield = req.getParameter("parsefield");

		String parser2 = req.getParameter("parser2");
		String parsefield2 = req.getParameter("parsefield2");

		String title = req.getParameter("title");

		String startdate = req.getParameter("startdate");
		String stopdate = req.getParameter("stopdate");

		String course = req.getParameter("course");

		HashMap<String, String> optionmap = new HashMap<String, String>();

		// do not put date filter when not set
		if (req.getParameter("dateselector").equals("true")) {
			System.out.println("datefilter");
			optionmap.put("startdate", startdate);
			optionmap.put("enddate", stopdate);
		}

		if (course != "all") {
			optionmap.put("course", course);
		}
		System.out.println(startdate);
		// CREATE QUERY
		QueryFactory factory = new QueryFactory();
		Query query = factory.createQuery(optionmap, null);

		// student filter
		query.addOption(new StudentFilter(student));

		Long id = (long) 0;

		Graph graph = null;
		if (maintype.equals("normal")) {
			System.out.println("normal");
			// add groupfield
			query.addGroup(new Group(GroupField.valueOf(groupfield)));
			graph = new XYGraph(title, student, query,
					GraphType.valueOf(graphtype),
					ParseField.valueOf(parsefield), Parser.valueOf(parser));
		} else if (maintype.equals("stacked")) {
			System.out.println("stacked");
			query.addGroup(new Group(GroupField.valueOf(groupfield)));
			query.addGroup(new Group(GroupField.valueOf(subgroupfield)));
			graph = new StackedGraph(title, student, query,
					GraphType.valueOf(graphtype),
					ParseField.valueOf(parsefield), Parser.valueOf(parser));
		} else if (maintype.equals("scatter")) {
			System.out.println("scatter");
			graph = new ScatterGraph(title, student, query, GraphType.SCATTER,
					ParseField.valueOf(parsefield), Parser.valueOf(parser),
					ParseField.valueOf(parsefield2), Parser.valueOf(parser2));
		}

		if (graph != null) {
			System.out.println("PUT GRAPH");
			Registry.graphFinder().put(graph);
		}

		resp.sendRedirect("/graph/graphs.jsp?showgraph=" + graph.getId());

	}
}

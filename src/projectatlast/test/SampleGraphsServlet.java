package projectatlast.test;

import projectatlast.data.Registry;
import projectatlast.graph.*;
import projectatlast.group.Group;
import projectatlast.group.GroupField;
import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.*;

public class SampleGraphsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		resp.getWriter().println("bla");
		Date now = new Date();
		Date tomorrow = new Date(now.getTime() + 86400000);

		Student student = AuthController.getCurrentStudent();

		// TEST QUERY

		Query query = new Query();
		Date from = new Date();
		from.setMonth(9);
		Date to = new Date();
		to.setMonth(1);
		to.setYear(8000);
		DateFilter d = new DateFilter(from, to);
		query.addOption(d);
		query.addGroup(new Group(GroupField.COURSE));

		XYGraph graph1 = new XYGraph("newqueryXY", student, query,
				GraphType.COLUMN, ParseField.DURATION, Parser.SUM);

		query = new Query();
		query.addGroup(new Group(GroupField.COURSE));
		query.addGroup(new Group(GroupField.TYPE));
		StackedGraph graph2 = new StackedGraph("Stacked", student, query,
				GraphType.COLUMN, ParseField.DURATION, Parser.SUM);

		query = new Query();
		query.addGroup(new Group(GroupField.HOUR_OF_DAY));
		query.addGroup(new Group(GroupField.COURSE));
		StackedGraph graph3 = new StackedGraph("Stacked Inversed", student,
				query, GraphType.COLUMN, ParseField.DURATION, Parser.SUM);
		query = new Query();
		query.addGroup(new Group(GroupField.ACTIVITY));
		ScatterGraph graph4 = new ScatterGraph("SCATTER", student, query,
				GraphType.SCATTER, ParseField.MOOD_COMPREHENSION, Parser.SUM,
				ParseField.MOOD_INTEREST, Parser.SUM);

//		XYGraph graph2 = new XYGraph("gen: SUM", student, query,
//				SortField.COURSE, ParseField.DURATION, Parser.SUM,
//				GraphType.COLUMN);
//
//		XYGraph graph3 = new XYGraph("gen: MIN", student, query,
//				SortField.COURSE, ParseField.DURATION, Parser.MIN,
//				GraphType.BAR);
//
//		XYGraph graph4 = new XYGraph("gen: MIN PIE", student, query,
//				SortField.COURSE, ParseField.DURATION, Parser.MIN,
//				GraphType.PIE);
//
//		Registry.graphFinder().putGraph(graph4);
//
//		List<Graph> graphs = GraphController.getAllFromStudent(AuthController
//				.getCurrentStudent());

		Registry.graphFinder().put(graph1);
		Registry.graphFinder().put(graph2);
		Registry.graphFinder().put(graph3);
		Registry.graphFinder().put(graph4);

	}
}

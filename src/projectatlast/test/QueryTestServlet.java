package projectatlast.test;

import projectatlast.group.*;
import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.*;

public class QueryTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		resp.getWriter().println("Query:");
		Query q = new Query();
		
		Student student = AuthController.getCurrentStudent();
		q.addOption(new StudentFilter(student));
		resp.getWriter().println("student: "+student.toString());

		// Start date
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DATE, 29);
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date from = cal.getTime();
		resp.getWriter().println("from: "+from.toString());

		// End date
		cal.set(Calendar.DATE, 30);
		cal.set(Calendar.HOUR_OF_DAY, 18);
		cal.set(Calendar.MINUTE, 45);
		Date to = cal.getTime();
		resp.getWriter().println("to: "+to.toString());
		
		// Create date filter
		DateFilter df = new DateFilter();
		df.from(from);
		df.to(to);
		q.addOption(df);
		
		// Groups
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group(GroupField.COURSE));
		groups.add(new Group(GroupField.TYPE));
		q.setGroups(groups);

		// Run query
		long tic = System.currentTimeMillis();
		Groupable<Activity> results = q.get();
		long toc = System.currentTimeMillis();
		resp.getWriter().println();
		resp.getWriter().printf("Query completed in %d ms", toc - tic);
		resp.getWriter().println();
		resp.getWriter().println();
		
		// Created groups
		resp.getWriter().println("Groups:");
		int depth;
		for(ListIterator<Group> it = groups.listIterator(); it.hasNext(); it.next()) {
			depth = it.nextIndex() + 1;
			resp.getWriter().println(depth + ") " + results.getKeys(depth));
		}
		resp.getWriter().println();

		// List results
		resp.getWriter().println("Results:");
		printGroup(resp.getWriter(), results, 0);
	}
	
	private void printGroup(PrintWriter writer, Groupable<Activity> group, int depth) {
		String indent = new String(new char[depth]).replace("\0", "\t");
		writer.println(indent + "* " + group.getKey());
		if(group.isLeaf()) {
			for(Activity a : group.getValues()) {
				writer.println(indent + "\t" + a.toString());
			}
		} else {
			for(Groupable<Activity> child : group.getChildren()) {
				// To recurse or not to recurse, that is the question.
				printGroup(writer, child, depth+1);
			}
		}
	}
}

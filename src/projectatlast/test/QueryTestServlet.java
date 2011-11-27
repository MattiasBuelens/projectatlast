package projectatlast.test;

import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONException;

public class QueryTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		cal.set(Calendar.DATE, 10);
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date from = cal.getTime();
		resp.getWriter().println("from: "+from.toString());

		// End date
		cal.set(Calendar.DATE, 20);
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 45);
		Date to = cal.getTime();
		resp.getWriter().println("to: "+to.toString());
		
		// Create date filter
		DateFilter df = new DateFilter();
		df.from(from);
		df.to(to);
		q.addOption(df);

		// Run query
		long tic = System.currentTimeMillis();
		Groupable<Activity> results = q.get();
		long toc = System.currentTimeMillis();
		resp.getWriter().println();
		resp.getWriter().printf("Query completed in %d ms", toc - tic);
		resp.getWriter().println();
		resp.getWriter().println();

		// List results
		resp.getWriter().println("Results:");
		List<Activity> activities = results.getValues();
		for(Activity activity : activities) {
			try {
				resp.getWriter().println(activity);
				resp.getWriter().println(activity.toJSON());
				resp.getWriter().println();
			} catch (JSONException e) {}
		}
		resp.getWriter().println("EOF");
	}
}

package projectatlast;

import projectatlast.query.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class GroupServletTest extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		Query q = new Query();
		Date from = new Date();
		Date to = new Date(from.getTime() + 86400000);
		q.addOption(new DateFilter(from, to));
		Map<Object, List<Activity>> results = q.get();
		resp.getWriter().println("Query results:");
		resp.getWriter().println(results);
		resp.getWriter().println();

		resp.getWriter().println("Group test:");
		Course analyse = new Course("1", "Analyse", 10);
		Course mechanica = new Course("2", "Mechanica", 10);

		StudyActivity an = new StudyActivity(analyse);
		StudyActivity me = new StudyActivity(mechanica);

		List<Activity> activities = new ArrayList<Activity>();

		activities.add(an);
		activities.add(an);
		activities.add(me);

		Group grouper = new Group(SortField.COURSE);

		Map<Object, List<Activity>> result = grouper.group(activities);

		for(Object key : result.keySet()) {
			resp.getWriter().println( ((Course)key).getName() );
			
			for(Activity activity : result.get(key)) {
				resp.getWriter().println("- " + activity.toString());
			}
		}
	}

}

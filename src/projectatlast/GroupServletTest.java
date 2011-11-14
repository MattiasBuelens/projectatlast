package projectatlast;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.plotting.XYData;
import projectatlast.plotting.XYPlot;
import projectatlast.query.*;
import projectatlast.student.*;
import projectatlast.tracking.Activity;
import projectatlast.tracking.StudyActivity;

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
		Course analyse = new Course("H01A0BN", "Analyse, deel 1", 10);
		Course mechanica = new Course("H01B0AN",
				"Toegepaste mechanica, deel 1", 10);
		
		
		Registry.dao().ofy().put(analyse,mechanica);

		Student student = AuthController.getCurrentStudent();

		if (student == null) {
			resp.getWriter().println("No logged in student to work with.");
		} else {
			String type = "theory";
			StudyActivity an = new StudyActivity(student, type, Registry.dao().key(analyse));
			StudyActivity me = new StudyActivity(student, type, Registry.dao().key(mechanica));

			List<Activity> activities = new ArrayList<Activity>();

			activities.add(an);
			activities.add(an);
			activities.add(me);

			Group grouper = new Group(SortField.COURSE);

			
			//show xy
			XYPlot plot = new XYPlot(activities, SortField.COURSE, ParseField.DURATION, Parser.SUM);
			
			XYData data= plot.generateXYData();
			
			List<Object> xdata = data.getX();
			List<Long> ydata = data.getY();
			
			for(Object o:xdata){
				System.out.println("x: "+o.toString());
			}
			for(long o:ydata){
				System.out.println("y: "+o);
			}
			
			
			
			
			Map<Object, List<Activity>> result = grouper.group(activities);

			for (Object key : result.keySet()) {
				resp.getWriter().println(((Course) key).getName());

				for (Activity activity : result.get(key)) {
					resp.getWriter().println("- " + activity.toString());
				}
			}
		}
	}

}

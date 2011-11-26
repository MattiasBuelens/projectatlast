package projectatlast.graph;

import projectatlast.course.*;
import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.*;
import projectatlast.tracking.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.*;

public class XYDataServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Query q = new Query();
	Date now = new Date();
	Date tomorrow = new Date(now.getTime() + 86400000);
	q.addOption(new DateFilter(now, tomorrow));
		
		

		
		
		resp.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = resp.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		
		Student student = AuthController.getCurrentStudent();
		List<Object> xdata =null;
		List<Long> ydata = null;

		

			/** TEST QUERY **/
			
			Query query = new Query();
			Date from = new Date();
			from.setMonth(9);
			Date to = new Date();
			DateFilter d = new DateFilter(from, to);
			query.addOption(d);
			
			
			
			/**List<Activity> activities = query.exec();*/
			
			//temporary solution:
			List<Activity> activities = Registry.activityFinder().findByStudent(student);

			//Group grouper = new Group(SortField.COURSE);

			// show xy
			XYGraph plot = new XYGraph("testplot",student,query, SortField.COURSE,
					ParseField.DURATION, Parser.MAX);
			
			

			XYData data = plot.generateXYData();
			xdata = data.getX();
			ydata = data.getY();
			
			Long graphid = Long.parseLong(req.getParameter("id"));
			//resp.getWriter().println(graphid);
			//System.out.println("ID: "+graphid);
			XYGraph gr =  (XYGraph)Registry.graphFinder().getGraph(graphid);
			XYData data2 = gr.generateXYData();
			System.out.println("data2 : "+data2.getX().size());
			xdata = data2.getX();
			ydata = data2.getY();
			
			
			System.out.println("lengete" +ydata.size());
		
		
		
		JSONArray array = new JSONArray();
		

		// Output as JSON
		resp.setContentType("application/json");
		JSONWriter writer = new JSONWriter(resp.getWriter());
		try {
			writer.object().key("x").array();
			for (Object i : xdata) {
				writer.value(i);
			}
			writer.endArray();
			
			writer.key("y").array();
			for (Long i : ydata) {
				writer.value(i);
			}
			writer.endArray();
			
			writer.key("title").value(gr.getTitle());
			writer.endObject();
			
		} catch (JSONException e) {
			resp.getWriter().println("{\"y\":null}");
		}
		

		out.flush();
	}
}

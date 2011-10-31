package projectatlast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.*;

import projectatlast.query.Group;
import projectatlast.query.SortField;

@SuppressWarnings("serial")
public class groupservletTest extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
	
		Course analyse = new Course("1", "analyse", 10);
		Course mechanica = new Course("2", "mechanica", 10);
		
		StudyActivity an = new StudyActivity(analyse);
		StudyActivity me = new StudyActivity(mechanica);
		
		List<Activity> activities = new ArrayList<Activity>();
		
		activities.add(an);
		activities.add(an);
		activities.add(me);
		
		Group grouper = new Group(SortField.COURSE);
		
		Map<Object,List<Activity>> result = grouper.group(activities);
		
		for(int i=0;i<result.keySet().size();i++){
			resp.getWriter().println(((Course)(result.keySet().toArray()[i])).getName());
			
			for(int j =0; j<result.get(result.keySet().toArray()[i]).size();j++ ){
				resp.getWriter().println("- "+result.get(result.keySet().toArray()[i]).get(j).toString());
			}
		}
	}


}

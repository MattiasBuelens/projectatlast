package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.http.*;

public class CreateActivityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		{

			String activityType = (String) req.getAttribute("activityType");
			String type = (String) req.getAttribute("type");
			
	
			
			
			if(activityType=="freetime"){
				FreeTimeActivity activity = new FreeTimeActivity(AuthController.getCurrentStudent(), type);
				ActivityController.startFreeTimeActivity(activity);
				AuthController.getCurrentStudent().setActivity(Registry.activityFinder().getKey(activity));
				Registry.dao().ofy().put(AuthController.getCurrentStudent());
				
				System.out.println("create");
			
			}else if(activityType=="study"){
				//StudyActivity activity = new StudyActivity(AuthController.getCurrentStudent(), type, course)
			
			}
			
			
			resp.sendRedirect("/home.jsp");
		}
		
	}
}

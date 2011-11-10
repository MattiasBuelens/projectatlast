package projectatlast.tracking;

import projectatlast.student.AuthController;

import java.io.IOException;

import javax.servlet.http.*;

public class CreateActivityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		{

			String activityType = (String) req.getAttribute("activitytype");
			String type = (String) req.getAttribute("type");
			
			if(activityType=="freetimeactivity"){
				FreeTimeActivity activity = new FreeTimeActivity(AuthController.getCurrentStudent(), type);
				AuthController.getCurrentStudent().startFreeTimeActivity(activity);
			}
			
			resp.sendRedirect("/home.jsp");
		}
		
	}
}

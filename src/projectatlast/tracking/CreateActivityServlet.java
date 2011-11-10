package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.http.*;

public class CreateActivityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		{

			String activityType = (String) req.getParameter("activityType");

			String type = (String) req.getParameter("type");
			
			Student currentStudent = AuthController.getCurrentStudent();

			System.out.println(activityType);
			if(activityType.equals("freetime")){

				FreeTimeActivity activity = new FreeTimeActivity(currentStudent, type);

				ActivityController.startFreeTimeActivity(activity);

				currentStudent.setActivity(Registry.activityFinder().getKey(activity));
				
				Registry.dao().ofy().put(currentStudent);
				System.out.println(AuthController.getCurrentStudent().getActivity());
				
			
			}else if(activityType=="study"){
				//StudyActivity activity = new StudyActivity(AuthController.getCurrentStudent(), type, course)
			
			}
			
			
			resp.sendRedirect("/home.jsp");
		}
		
	}
}

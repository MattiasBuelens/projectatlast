package projectatlast.tracking;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.http.*;

public class StopActivityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		{
			
			Student currentStudent = AuthController.getCurrentStudent();
			
			//stop activity and put
			Activity activity = Registry.dao().ofy().get(currentStudent.getActivity());
			activity.stop();
			Registry.dao().ofy().put(activity);
			
			//set activity in current user to null and put student
			currentStudent.setActivity(null);
			Registry.dao().ofy().put(currentStudent);
			
			resp.sendRedirect("/home.jsp");
			
		}
		
	}
}

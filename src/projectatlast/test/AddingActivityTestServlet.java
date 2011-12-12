package projectatlast.test;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

public class AddingActivityTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
			Student cu = AuthController.getCurrentStudent();
			Date startDate = new Date();
			Date stopDate = new Date();
			
			stopDate.setHours(stopDate.getHours()+1);
			
			Course analyse = Registry.courseFinder().getCourse("H01A0B");
			Activity a1 = new StudyActivity(cu, "Exercises", analyse);
			
			a1.setStart(startDate);
			a1.setEnd(stopDate);
			ActivityController.put(true, a1);
			
			resp.getWriter().println("SUCCES");
			
			
	}
}

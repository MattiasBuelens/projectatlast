package projectatlast.milestone;

import projectatlast.course.Course;
import projectatlast.student.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddMilestoneServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;

		public void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws IOException, ServletException {
			
			// Get current student
			Student student = AuthController.getCurrentStudent();
			
			// Get enrolled courses of current student
			List<Course> courses = SettingsController.getCourses(student);
			req.setAttribute("studentCourses", courses);

			req.getRequestDispatcher("/milestone/addMilestone.jsp").forward(req, resp);
		}

}

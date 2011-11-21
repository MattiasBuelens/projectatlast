package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.*;
import projectatlast.course.Course;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddMilestoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		// make the student-info available for the .jsp-file
		
		// Get current student
		Student student = AuthController.getCurrentStudent();
		

		// Get enrolled courses of current student
		List<Course> courses = SettingsController.getCourses(student);
		req.setAttribute("studentCourses", courses);
		
		req.getRequestDispatcher("/milestone/create.jsp").forward(req,resp);
		
		// get parameters

		// enumeration ids
		int operator = Integer.parseInt(req.getParameter("operator"));
		int parsefield = Integer.parseInt(req.getParameter("parsefield"));
		int parser = Integer.parseInt(req.getParameter("parser"));
		int startDate = Integer.parseInt(req.getParameter("startDate"));
		int stopDate = Integer.parseInt(req.getParameter("stopDate"));

		// other params
			//COURSENAME HAS TO BE FINISHED!!!
		String selectedCourse = req.getParameter("course");
		String goal_str = req.getParameter("goal");
		long goal = Long.parseLong(goal_str);

		// get the corresponding enumerations
		ComparativeOperator operator_enum = ComparativeOperator.values()[operator];
		ParseField parsefield_enum = ParseField.values()[parsefield];
		Parser parser_enum = Parser.values()[parser];

		// Create milestone
		Query query = null;
		Milestone milestone = new Milestone(student, goal, new Date(),
				operator_enum, query, parser_enum, parsefield_enum);

		// put the milestone
		/** milestonefinder not ready yet: temporary method **/

		Registry.dao().ofy().put(milestone);
		
	}

}

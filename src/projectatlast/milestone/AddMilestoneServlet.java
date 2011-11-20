package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddMilestoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// get parameters

		// enumeration ids
		int operator = Integer.parseInt(req.getParameter("operator"));
		int parsefield = Integer.parseInt(req.getParameter("parsefield"));
		int parser = Integer.parseInt(req.getParameter("parser"));

		// other params
		String goal_str = req.getParameter("goal");
		long goal = Long.parseLong(goal_str);

		// get the corresponding enumerations
		ComparativeOperator operator_enum = ComparativeOperator.values()[operator];
		ParseField parsefield_enum = ParseField.values()[parsefield];
		Parser parser_enum = Parser.values()[parser];

		// Get current student
		Student student = AuthController.getCurrentStudent();

		// Create milestone
		Query query = null;
		Milestone milestone = new Milestone(student, goal, new Date(),
				operator_enum, query, parser_enum, parsefield_enum);

		// put the milestone
		/** milestonefinder not ready yet: temporary method **/

		Registry.dao().ofy().put(milestone);

		// req.getRequestDispatcher("/milestone/addMilestone.jsp").forward(req,
		// resp);
	}

}

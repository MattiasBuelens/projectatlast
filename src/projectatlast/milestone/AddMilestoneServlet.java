package projectatlast.milestone;

import projectatlast.course.Course;
import projectatlast.query.*;
import projectatlast.student.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddMilestoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Get current student
		Student student = AuthController.getCurrentStudent();

		// Get enrolled courses of current student
		List<Course> courses = StudentController.getCourses(student);
		req.setAttribute("studentCourses", courses);

		// Get free time activity types
		List<String> freeTimeTypes = StudentController.getFreeTimeTypes(student);
		req.setAttribute("freeTimeTypes", freeTimeTypes);

		req.getRequestDispatcher("/milestone/add.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Current student
		Student student = AuthController.getCurrentStudent();

		// Enumeration identifiers
		String operatorId = req.getParameter("operator");
		String parsefieldId = req.getParameter("parsefield");
		String parserId = req.getParameter("parser");

		// Corresponding enumerations
		ComparativeOperator operator = ComparativeOperator.fromId(operatorId);
		ParseField parseField = ParseField.fromId(parsefieldId);
		Parser parser = Parser.fromId(parserId);

		// Activity kind
		String kind = req.getParameter("activity-type");

		// Goal
		double goal = 0;
		try {
			goal = Double.parseDouble(req.getParameter("goal"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "The goal you entered was invalid.");
			doGet(req, resp);
			return;
		}

		// Deadline
		Date deadline = null;
		try {
			deadline = new SimpleDateFormat("dd-MM-yyyy").parse(req
					.getParameter("stopdate"));
		} catch (ParseException e) {}
		
		//Sets the time of the deadline to the end of the day. This means 23h 59m of the given day.
		Calendar cal = Calendar.getInstance();
		cal.setTime(deadline);
		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.SECOND, -1);
		deadline = cal.getTime();

		// Query parameters
		Map<String, String> optionMap = new LinkedHashMap<String, String>();
		optionMap.put("student", "current");
		optionMap.put("kind", kind);
		optionMap.put("startdate", req.getParameter("startdate"));
		optionMap.put("stopdate", req.getParameter("stopdate"));
		if (kind.equalsIgnoreCase("study")) {
			optionMap.put("course", req.getParameter("course"));
			optionMap.put("type", req.getParameter("study-type"));
		} else if(kind.equalsIgnoreCase("freetime")) {
			optionMap.put("type", req.getParameter("freetime-type"));
		}
		// Query groups
		List<String> queryGroups = Collections.emptyList();
		// Create query
		Query query = new QueryFactory().createQuery(optionMap, queryGroups);

		// Create milestone
		MilestoneController.createMilestone(student,
				goal,
				deadline,
				query,
				parser,
				parseField,
				operator);

		// Redirect to milestone overview
		resp.sendRedirect("/milestone/milestones.jsp");
	}
}

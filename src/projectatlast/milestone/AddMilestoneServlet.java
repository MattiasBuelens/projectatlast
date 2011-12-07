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

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Get current student
		Student student = AuthController.getCurrentStudent();
		
		// Get enrolled courses of current student
		List<Course> courses = SettingsController.getCourses(student);
		List<String> freeTimeTypes = student.getFTActs();
		req.setAttribute("studentCourses", courses);
		req.setAttribute("freeTimeTypes", freeTimeTypes);
		req.getRequestDispatcher("/milestone/addMilestone.jsp").forward(req,
				resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Current student
		Student student = AuthController.getCurrentStudent();

		// The sentence
		String sentence = req.getParameter("sentence");
		
		// Enumeration identifiers
		String operatorId = req.getParameter("operator");
		String parsefieldId = req.getParameter("parsefield");
		String parserId = req.getParameter("parser");
		
		// Corresponding enumerations
		ComparativeOperator operator = ComparativeOperator.fromId(operatorId);
		ParseField parseField = ParseField.fromId(parsefieldId);
		Parser parser = Parser.fromId(parserId);

		// Goal
		long goal = 0;
		try {
			goal = Long.parseLong(req.getParameter("goal"));
		} catch(NumberFormatException e) {}

		//StartDate
		
		Date startDate=null;
		try{
			startDate = new SimpleDateFormat("dd-MM-yyyy").parse(req.getParameter("startdate"));
		} catch(ParseException e){}
		
		// Deadline
		Date deadline = null;
		try {
			deadline = new SimpleDateFormat("dd-MM-yyyy").parse(req.getParameter("stopdate"));
		} catch (ParseException e) {}

		// Query parameters
		@SuppressWarnings("unchecked")
		Map<String, String> optionMap = new LinkedHashMap<String, String>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("ss-mm-hh-dd-MM-yyyy");
		
		optionMap.put("startdate", formatter.format(startDate)        );
		optionMap.put("stopdate" , formatter.format(deadline)         );
		optionMap.put("course"   , req.getParameter("course")         );
		optionMap.put("student"  , "current"                          );
		optionMap.put("kind"     , req.getParameter("activity-type")  );
		
	
		
		// Query groups
		List<String> queryGroups = Collections.emptyList();

		// Create query
		Query query = new QueryFactory().createQuery(optionMap, queryGroups);

		// Create milestone
		MilestoneController.createMilestone(student,
				goal,
				deadline,
				sentence,
				query,
				parser,
				parseField,
				operator);
		
		resp.sendRedirect("/milestone/milestones.jsp");
	}
}

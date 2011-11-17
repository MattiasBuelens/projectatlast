package projectatlast.test;

import projectatlast.course.Course;
import projectatlast.course.StudyProgram;
import projectatlast.data.Registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

public class SampleCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		// Bbi-sem1
		List<Course> bbi1Courses = new ArrayList<Course>();
		bbi1Courses.add(new Course("H01A8A", "Algemene en technische scheikunde", 7));
		bbi1Courses.add(new Course("H01B0A", "Toegepaste mechanica, deel 1", 5));
		bbi1Courses.add(new Course("H01A4A", "Toegepaste algebra", 5));
		bbi1Courses.add(new Course("H01A0B", "Analyse, deel 1", 6));
		bbi1Courses.add(new Course("H01C4B", "Wijsbegeerte", 3));
		bbi1Courses.add(new Course("H01B9A", "Probleemoplossen en ontwerpen, deel 1", 4));
		Registry.dao().ofy().put(bbi1Courses).keySet();
		resp.getWriter().printf("Created %d courses for Bbi-sem1", bbi1Courses.size());
		resp.getWriter().println();
		resp.getWriter().println(bbi1Courses);
		
		StudyProgram bbi1 = new StudyProgram("Bbi-sem1", "Burgerlijk ingenieur: eerste semester", bbi1Courses);
		Registry.dao().ofy().put(bbi1);
		resp.getWriter().printf("Created study program Bbi-sem1");
		resp.getWriter().println();
		resp.getWriter().println(bbi1);

		// Bbi-sem3
		List<Course> bbi3Courses = new ArrayList<Course>();
		bbi3Courses.add(new Course("H01C6A", "Organische scheikunde", 3));
		bbi3Courses.add(new Course("H01C8A", "Toegepaste mechanica, deel 2", 5));
		bbi3Courses.add(new Course("H01D8B", "Numerieke wiskunde", 4));
		bbi3Courses.add(new Course("H01A6A", "Kansrekenen en statistiek", 3));
		bbi3Courses.add(new Course("H08W0A", "Analyse, deel 3", 3));
		bbi3Courses.add(new Course("H01D2A", "Informatie-overdracht en -verwerking", 5));
		bbi3Courses.add(new Course("H01D7B", "Economie", 3));
		bbi3Courses.add(new Course("H01D4B", "Probleemoplossen en ontwerpen, deel 3", 4));
		Registry.dao().ofy().put(bbi3Courses).keySet();
		resp.getWriter().printf("Created %d courses for Bbi-sem3", bbi3Courses.size());
		resp.getWriter().println();
		resp.getWriter().println(bbi3Courses);
		
		StudyProgram bbi3 = new StudyProgram("Bbi-sem3", "Burgerlijk ingenieur: derde semester", bbi3Courses);
		Registry.dao().ofy().put(bbi3);
		resp.getWriter().printf("Created study program Bbi-sem3");
		resp.getWriter().println();
		resp.getWriter().println(bbi3);
	}

}

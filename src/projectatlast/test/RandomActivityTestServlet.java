package projectatlast.test;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

public class RandomActivityTestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, 8);
		int randDay = new Random().nextInt(cal.getActualMaximum(Calendar.DATE)-1) + 1;
		cal.set(Calendar.DATE, randDay);
		int randHour = new Random().nextInt(cal.getActualMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.HOUR_OF_DAY, randHour);
		int randMinute = new Random().nextInt(cal.getActualMaximum(Calendar.MINUTE));
		cal.set(Calendar.MINUTE, randMinute);
		cal.set(Calendar.SECOND, 0);
		Date s1 = cal.getTime();
		int randDuration = new Random().nextInt(7*24*60*60);
		cal.add(Calendar.SECOND, randDuration);
		Date e1 = cal.getTime();

		Student student = AuthController.getCurrentStudent();
		Course analyse = Registry.courseFinder().getCourse("H01A0B");
		Activity a1 = new StudyActivity(student, "Exercises", analyse);
		a1.setStart(s1);
		a1.setEnd(e1);
		ActivityController.put(a1);
		resp.getWriter().println("Created: "+a1);
	}
}

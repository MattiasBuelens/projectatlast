package projectatlast.test;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;

public class DemoActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int nActivities = 10;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		List<Activity> activities = new ArrayList<Activity>();
		Random rand = new Random();

		// Collect data
		Calendar cal = Calendar.getInstance();
		Student student = AuthController.getCurrentStudent();
		List<Course> courses = student.getCourses();
		List<String> studyTypes = new ArrayList<String>(StudyActivity
				.getTypes().keySet());
		List<String> freeTimeTypes = student.getFreeTimeTypes();
		List<String> socials = Arrays.asList(new String[] { "Alone", "Two",
				"Group" });
		List<String> tools = student.getTools();
		List<String> locations = student.getLocations();

		for (int i = 0; i < nActivities; ++i) {
			// Random date in November or December
			cal.set(Calendar.YEAR, 2011);
			cal.set(Calendar.MILLISECOND, 0);
			int randMonth = Calendar.NOVEMBER + rand.nextInt(1);
			cal.set(Calendar.MONTH, randMonth);
			int randDay = 1 + rand
					.nextInt(cal.getActualMaximum(Calendar.DATE) - 1);
			cal.set(Calendar.DATE, randDay);
			int randHour = rand.nextInt(cal
					.getActualMaximum(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.HOUR_OF_DAY, randHour);
			int randMinute = rand
					.nextInt(cal.getActualMaximum(Calendar.MINUTE));
			cal.set(Calendar.MINUTE, randMinute);
			cal.set(Calendar.SECOND, 0);
			Date startDate = cal.getTime();
			int randDuration = rand.nextInt(3 * 60 * 60); // max 3 hours
			cal.add(Calendar.SECOND, randDuration);
			Date endDate = cal.getTime();

			boolean isStudy = rand.nextBoolean();
			if (isStudy) {
				// Random data
				Course randCourse = courses.get(rand.nextInt(courses.size()));
				String randType = studyTypes
						.get(rand.nextInt(studyTypes.size()));
				String randSocial = socials.get(rand.nextInt(socials.size()));
				String randTool = tools.get(rand.nextInt(tools.size()));
				String randLocation = locations.get(rand.nextInt(locations
						.size()));
				Mood randMood = new Mood(rand.nextInt(100), rand.nextInt(100));
				// Create activity
				StudyActivity activity = new StudyActivity(student, randType,
						randCourse);
				activity.setStart(startDate);
				activity.setEnd(endDate);
				activity.setSocial(randSocial);
				activity.addTool(randTool);
				activity.setLocation(randLocation);
				activity.setMood(randMood);
				if (randType.equalsIgnoreCase("study")) {
					long randPages = rand.nextInt(randDuration) / 180;
					activity.setPages(randPages);
				}
				activities.add(activity);
			} else {
				// Random data
				String randType = freeTimeTypes.get(rand.nextInt(freeTimeTypes
						.size()));
				// Create activity
				FreeTimeActivity activity = new FreeTimeActivity(student,
						randType);
				activity.setStart(startDate);
				activity.setEnd(endDate);
				activities.add(activity);
			}
		}

		// Put activities
		ActivityController.put(true, activities);
	}
}

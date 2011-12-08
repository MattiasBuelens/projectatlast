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

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Student student = AuthController.getCurrentStudent();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2011);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		
		for(int i=0;i<30;i++)
		{
			cal.set(Calendar.DATE, i);
			int randHour = 9+ new Random().nextInt(12);
			cal.set(Calendar.HOUR_OF_DAY, randHour);
			int randMinute = new Random().nextInt(cal.getActualMaximum(Calendar.MINUTE));
			cal.set(Calendar.MINUTE, randMinute);
			cal.set(Calendar.SECOND, 0);
			Date s1 = cal.getTime();
			int randDuration = new Random().nextInt(3*60*60);
			cal.add(Calendar.SECOND, randDuration);
			Date e1 = cal.getTime();
			
			int freeTimeOrStudyActivity = new Random().nextInt(1);
			
			if(freeTimeOrStudyActivity == 0)
			{
	
				Course[] coursesArray = student.getCourses().toArray(new Course[student.getCourses().size()]);
				int randCourse = new Random().nextInt(coursesArray.length);
				Course newCourse = coursesArray[randCourse];
	
				String[] typesArray =  StudyActivity.getTypes().values().toArray(new String[StudyActivity.getTypes().size()]);
				int randType = new Random().nextInt(typesArray.length);
				String newType = typesArray[randType];
				
				Activity a1 = new StudyActivity(student, newType, newCourse);
				a1.setStart(s1);
				a1.setEnd(e1);
				ActivityController.put(a1);
				
				long activityId = a1.getId();
				
				long randPages = (long) new Random().nextInt((randDuration));
				randPages = ((randPages*20)/3600);
				
				String[] socialsArray = new String[3]; 
				socialsArray[0]="Alone";
				socialsArray[1]="Two";
				socialsArray[2]="Group";
				int randSocial = new Random().nextInt(2);
				String newSocial = socialsArray[randSocial];

				String[] toolsArray = student.getTools().toArray(new String[student.getTools().size()]);
				int randTool = new Random().nextInt(toolsArray.length);
				String[] newTool = new String[1];
				newTool[0] = toolsArray[randTool];
				
				String[] locationsArray = student.getLocations().toArray(new String[student.getLocations().size()]);
				int randLocation = new Random().nextInt(locationsArray.length);
				String newLocation = locationsArray[randLocation];
				
				long randMoodInterest = (long) new Random().nextInt(100);
				long randMoodComprehension = (long) new Random().nextInt(100);
				
				ActivityController.updateStudyActivity(activityId, randPages,
						newSocial, newTool, newLocation, randMoodInterest,
						randMoodComprehension);
			}
			
			else
			{
				String[] typesArray = student.getFreeTimeTypes().toArray(new String[student.getFreeTimeTypes().size()]);
				int randType = new Random().nextInt(typesArray.length);
				String newType = typesArray[randType];
				
				Activity a2 = new FreeTimeActivity(student, newType);
				a2.setStart(s1);
				a2.setEnd(e1);
				ActivityController.put(a2);
				
			}
		}
	}
}

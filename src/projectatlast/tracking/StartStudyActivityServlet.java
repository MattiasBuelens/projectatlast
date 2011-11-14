package projectatlast.tracking;

import projectatlast.course.Course;
import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;

import java.io.IOException;

import javax.servlet.http.*;

import com.googlecode.objectify.Key;

public class StartStudyActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		// get needed via post/get parameters
		String type = req.getParameter("type");
		String courseid = req.getParameter("course"); //get course id
		
		//Get the key of the course belonging to the courseid
		//Fetch the Course Entitiy of the course by using the courseFinder(id)
		//Request the key of the resulting entity
		Key<Course> coursekey = Registry.dao().key(Registry.courseFinder().getCourse(courseid));
		
		Student currentStudent = AuthController.getCurrentStudent();

		
		
		//Create New FreeTimeActivity
		StudyActivity activity = new StudyActivity(currentStudent, type, coursekey);

		//Start a new activity using ActivityController
		ActivityController.startActivity(activity);
		
		//Set the current activity on the currentStudent
		currentStudent.setActivity(Registry.activityFinder().getKey(activity));

		//Put the student to the datastore
		Registry.dao().ofy().put(currentStudent);

		
		
		//redirect to the home page
		resp.sendRedirect("/home.jsp");
	}
}

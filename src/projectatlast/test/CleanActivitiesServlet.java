package projectatlast.test;

import projectatlast.data.Registry;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;
import projectatlast.tracking.ActivitySlice;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;

public class CleanActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		Student student = AuthController.getCurrentStudent();
		
		Objectify ofy = Registry.dao().ofy();
		resp.getWriter().println("Cleaning activities...");
		QueryResultIterable<Key<Activity>> activities = ofy.query(Activity.class).filter("student =", student).fetchKeys();
		ofy.delete(activities);
		resp.getWriter().println("Cleaning activity slices...");
		QueryResultIterable<Key<ActivitySlice>> slices = ofy.query(ActivitySlice.class).filter("student =", student).fetchKeys();
		ofy.delete(slices);
		resp.getWriter().println("Done.");
	}
}

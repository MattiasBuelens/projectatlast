package projectatlast.course;

import projectatlast.student.SettingsController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONWriter;

public class AjaxProgramCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get the courses from the study program
		String programId = req.getParameter("studyProgram");
		List<Course> programCourses = SettingsController.getProgramCourses(programId);

		// Output as JSON
		resp.setContentType("application/json");
		JSONWriter writer = new JSONWriter(resp.getWriter());
		try {
			writer.object().key("courses").array();
			for (Course course : programCourses) {
				writer.value(course.toJSON());
			}
			writer.endArray().endObject();
		} catch (JSONException e) {
			resp.getWriter().println("{\"courses\":null}");
		}
	}
}

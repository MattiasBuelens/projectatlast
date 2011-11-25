package projectatlast.course;

import projectatlast.student.SettingsController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONException;

public class AjaxProgramCoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("application/json");

		
		
		// Get the courses from the study program
		String programId = req.getParameter("studyProgram");
		StudyProgram program = SettingsController.getProgram(programId);
		if(program == null) {
			resp.getWriter().println("{\"courses\":[]}");
			return;
		}

		// Output as JSON
		try {
			program.toJSON().write(resp.getWriter());
		} catch (JSONException e) {
			resp.getWriter().println("{\"courses\":[]}");
		}
	}
}

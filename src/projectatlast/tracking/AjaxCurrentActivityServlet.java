package projectatlast.tracking;

import projectatlast.student.*;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONWriter;

public class AjaxCurrentActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		
		// Get current activity
		Student student = AuthController.getCurrentStudent();
		Activity activity = StudentController.getCurrentActivity(student);
		
		// Output as JSON
		JSONWriter writer = new JSONWriter(resp.getWriter());
		try {
			writer.object().key("activity");
			if(activity != null) {
				writer.value(activity.toJSON());
			} else {
				writer.value(null);
			}
			writer.endObject();
		} catch(JSONException e) {
			resp.getWriter().println("{\"activity\":null}");
		}
	}
}

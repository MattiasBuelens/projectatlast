package projectatlast.test;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

public class DataStoreCleanupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		List<Student> students = Registry.dao().ofy().query(Student.class).list();
		resp.getWriter().println("Fetching students...");
		for(Student student : students) {
			resp.getWriter().println("* "+student.getId());
			//student.setTools(null);
			resp.getWriter().println("  tools: "+student.getTools());
		}
		resp.getWriter().println("Putting students...");
		Registry.dao().ofy().put(students);
		resp.getWriter().println();
	}
}

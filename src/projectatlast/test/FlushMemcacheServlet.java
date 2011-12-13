package projectatlast.test;

import projectatlast.data.Registry;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.memcache.MemcacheService;

public class FlushMemcacheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		MemcacheService memcache = Registry.dao().memcache();
		memcache.clearAll();
		resp.getWriter().println("Memcache cleared");
	}
}

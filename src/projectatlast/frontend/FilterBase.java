package projectatlast.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FilterBase implements Filter {

	protected List<Pattern> excludePatterns = new ArrayList<Pattern>();

	/**
	 * Determines whether this filter should be applied to the current request.
	 * 
	 * An implementation of this class should call this method at the start of
	 * its doFilter method. If the result is false, the filter should abort and
	 * pass the request on to the next filter in the chain.
	 * 
	 * @param req
	 *            the servlet request
	 * @return true if filter should be applied, false otherwise.
	 */
	public boolean shouldFilter(HttpServletRequest req) {
		boolean result = true;
		String url = req.getRequestURI();
		for (Pattern excludePattern : excludePatterns) {
			if (excludePattern.matcher(url).matches()) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * @see {@link #shouldFilter(HttpServletRequest)}
	 */
	public boolean shouldFilter(ServletRequest req) {
		if (req instanceof HttpServletRequest)
			return shouldFilter((HttpServletRequest) req);
		return true;
	}

	/**
	 * Collects the exclude patterns from the init parameters.
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		String excludeParam = config.getInitParameter("exclude");
		if (excludeParam != null) {
			String[] excludes = excludeParam.split(",");
			for (String exclude : excludes) {
				excludePatterns.add(Pattern.compile(exclude,
						Pattern.CASE_INSENSITIVE));
			}
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		doFilter((HttpServletRequest) req, (HttpServletResponse) resp, chain);
	}

	public abstract void doFilter(HttpServletRequest req,
			HttpServletResponse resp, FilterChain chain) throws IOException,
			ServletException;

	@Override
	public void destroy() {
	}
}

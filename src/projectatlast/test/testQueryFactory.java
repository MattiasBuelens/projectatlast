package projectatlast.test;

import projectatlast.query.*;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;



public class testQueryFactory extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		HashMap<String, String> optionMap = new HashMap<String,String>();
		ArrayList<String>       groupList = new ArrayList<String>();
		
		optionMap.put("startdate", "10-55-23-01-03-2011");
		optionMap.put("stopdate",  "10-55-23-01-04-2011");
		optionMap.put("student", "current");
		
		groupList.add("TYPE");
		
		QueryFactory qf = new QueryFactory();
		
		Query query = qf.createQuery(optionMap, groupList);
		
		List<Option> options = query.getOptions();
	}
}

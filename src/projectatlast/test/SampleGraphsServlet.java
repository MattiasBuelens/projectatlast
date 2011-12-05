package projectatlast.test;

import projectatlast.data.Registry;
import projectatlast.graph.*;
import projectatlast.group.GroupField;

import projectatlast.query.*;
import projectatlast.student.AuthController;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.*;



public class SampleGraphsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
	
		resp.getWriter().println("bla");
	Date now = new Date();
	Date tomorrow = new Date(now.getTime() + 86400000);

		

		

	
		Student student = AuthController.getCurrentStudent();
		List<Object> xdata =null;
		List<Long> ydata = null;

		

			// TEST QUERY 
			
			Query query = new Query();
			Date from = new Date();
			from.setMonth(9);
			Date to = new Date();
			DateFilter d = new DateFilter(from, to);
			query.addOption(d);
			
			List<Activity> activities = Registry.activityFinder().findByStudent(student);
			resp.getWriter().println("ac "+activities.size());
			//Registry.dao().ofy().put(query);
			//Group grouper = new Group(SortField.COURSE);

			
			
			StackedGraph graph1 = new StackedGraph("teststacked2", student, activities, GroupField.TYPE, GroupField.USEDTOOLS, ParseField.DURATION, Parser.SUM, GraphType.COLUMN);
			
			StackedData data = new StackedData(graph1);
			List<Object> groups = data.getGroups();
			for(Object group: groups){
				resp.getWriter().println("group: "+group);
				
				List<Object> subgroups  = data.getSubGroups(group);
					
				for(Object subgroup: subgroups){
					resp.getWriter().print("	*"+subgroup);
					resp.getWriter().println(":"+data.getParsed(group,subgroup));
				}
				
			}

			
			
			/*
			// show xy
			XYGraph graph1 = new XYGraph("gen: MAXIMUM",student,query, SortField.COURSE,
					ParseField.DURATION, Parser.MAX,GraphType.BAR);
			
			XYGraph graph2 = new XYGraph("gen: SUM",student,query, SortField.COURSE,
					ParseField.DURATION, Parser.SUM,GraphType.COLUMN);
			
			XYGraph graph3 = new XYGraph("gen: MIN",student,query, SortField.COURSE,
					ParseField.DURATION, Parser.MIN,GraphType.BAR);
			
			XYGraph graph4 = new XYGraph("gen: MIN PIE",student,query, SortField.COURSE,
					ParseField.DURATION, Parser.MIN,GraphType.PIE);

			Registry.graphFinder().putGraph(graph4);
			
			
			List<Graph> g =  Registry.graphFinder().getGraphs(AuthController.getCurrentStudent());
			ArrayList<Graph> graphs = new ArrayList<Graph>(g);
			*/
			
			Registry.graphFinder().putGraph(graph1);
		
			
	}
}

package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.*;

import javax.persistence.Embedded;



import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class XYGraph extends Graph{
	


	protected XYGraph(){}
	

	public XYGraph(String title, Student student, Query query, SortField sortField,
			ParseField parseField, Parser parser) {
		super(title, student,query.exec(), sortField, parseField, parser); //temp
		



	}
	



	public XYData generateXYData() {
		// group the activities
		List<Activity> a = new ArrayList<Activity>(Registry.dao().ofy().get(this.activities).values());
		Map<Object, List<Activity>> grouped = new Group(sortField).group(a);

		System.out.println(grouped);
		List<Object> groups = new ArrayList<Object>(grouped.keySet());

		// use parser on every group;

		ArrayList<Long> parseResult = new ArrayList<Long>();
		List<Activity> activities2 = new ArrayList<Activity>();
		for (Object currentGroup : groups) {
			activities2 = grouped.get(currentGroup);
			long result = this.parser.parse(activities2, parseField);
			parseResult.add(result);
		}

		// generate XYData
		XYData data = new XYData(groups, parseResult);

		return data;

	}
	



}

package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Subclass;


@Subclass
public class StackedGraph extends Graph {

	SortField subgroup;
	SortField sortField;
	Parser parser;
	ParseField parseField;
	
	public StackedGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StackedGraph(String title, Student student,
			List<Activity> activities, SortField sortField,
			SortField subgroup,ParseField parseField, Parser parser, GraphType graphtype) {
		super(title, student, activities, graphtype);
		
		this.subgroup=subgroup;
		this.sortField=sortField;
		this.parser=parser;
		this.parseField=parseField;
		// TODO Auto-generated constructor stub
	}

	public StackedGraph(String title, Student student, Query query,
			SortField sortField, ParseField parseField, Parser parser,
			GraphType graphtype) {
		//super(title, student, query, graphtype);
		// TODO Auto-generated constructor stub
	}

	
	public StackedData getStackedData(){
		return new StackedData(this);
	}
	
	public List<Activity> getActivities(){
		return tempFetch();
	}
	
	private List<Activity> tempFetch(){
		ArrayList<Key<Activity>> l = new ArrayList<Key<Activity>>(Registry.dao().keys(Registry.activityFinder().findByStudent(getStudent())));
		return new ArrayList<Activity> (Registry.dao().ofy().get(l).values());

	}



	public SortField getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(SortField subgroup) {
		this.subgroup = subgroup;
	}

	public SortField getSortField() {
		return sortField;
	}

	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public ParseField getParseField() {
		return parseField;
	}

	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}
	
}

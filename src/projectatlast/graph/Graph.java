/**
 * This class represents a Graph
 * 
 * @author thomas goossens
 */
package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.query.Query;

import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public abstract class Graph {
	@Id Long id;
	
	Key<Student> student;	
	@Transient Key<Query> query;
	SortField sortField;
	ParseField parseField;
	Parser parser;
	String title;

	List<Key<Activity>> activities; //temporary until query ready
	
	protected Graph(){}
	
	

	public Graph(String title,Student student, Query query,
			SortField sortField, ParseField parseField, Parser parser) {
		super();
		setStudent(student);
		this.query = Registry.dao().key(query);
		this.sortField = sortField;
		this.parseField = parseField;
		this.parser = parser;
		this.title=title;
	}
	public Graph(String title,Student student, List<Activity> activities,
			SortField sortField, ParseField parseField, Parser parser) {
		super();
		setStudent(student);
		this.activities = new ArrayList<Key<Activity>>(Registry.dao().keys(activities));
		this.sortField = sortField;
		this.parseField = parseField;
		this.parser = parser;
		this.title=title;
	}
	
	
	
	public Student getStudent() {
		return Registry.studentFinder().getStudent(this.student);
	}
	
	
	
	public void setStudent(Student student) {
		this.student = Registry.studentFinder().getKey(student);
	}
	public Query getQuery() {
		return Registry.dao().ofy().get(query);
	}
	
	public void setQuery(Query query) {
		this.query = Registry.dao().key(query);
	}
	public SortField getSortField() {
		return sortField;
	}
	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}
	public ParseField getParseField() {
		return parseField;
	}
	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	
   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setStudent(Key<Student> student) {
		this.student = student;
		
	}
	
	

	
	
}

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
	
	String title;
	GraphType type;
	List<Key<Activity>> activities; //temporary until query ready
	GraphType graphtype;
	
	protected Graph(){}
	
	

	public Graph(String title,Student student, Query query,
 Parser parser,GraphType graphtype) {
		super();
		setStudent(student);
		this.query = Registry.dao().key(query);

		this.title=title;
		this.graphtype=graphtype;
	}
	public Graph(String title,Student student, List<Activity> activities,
		GraphType graphtype) {
		super();
		setStudent(student);
		
		//temporary disabled : this.activities = new ArrayList<Key<Activity>>(Registry.dao().keys(activities));

		this.title=title;
		this.graphtype=graphtype;
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
	
	public GraphType getGraphType(){
		return this.graphtype;
	}
	
	

	
	
}

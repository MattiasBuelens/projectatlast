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
import com.googlecode.objectify.annotation.Serialized;


@Entity
public abstract class Graph {
	@Id Long id;
	
	Key<Student> student;	
	@Serialized Query query;
	
	String title;
	GraphType type;
	List<Key<Activity>> activities; //temporary until query ready
	GraphType graphtype;
	
	protected Graph(){}
	
	

	public Graph(String title,Student student, Query query,GraphType graphtype) {
		super();
		setStudent(student);
		this.query = query;

		this.title=title;
		this.graphtype=graphtype;
	}
	
	public List<Activity> getQueryResult(){
		
		return Registry.activityFinder().findByStudent(getStudent());
		//return query.get().getValues();
	}
	
	public Graph(String title,Student student, List<Activity> activities,
		GraphType graphtype) {
		super();
		setStudent(student);
	
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
		return query;
	}
	
	public void setQuery(Query query) {
		this.query = query;
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

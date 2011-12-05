/**
 * This class represents a Graph
 * 
 * @author thomas goossens
 */
package projectatlast.graph;

import projectatlast.data.JSONable;
import projectatlast.data.Registry;
import projectatlast.group.Group;
import projectatlast.group.Groupable;
import projectatlast.query.*;
import projectatlast.query.Query;

import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.*;

import javax.persistence.*;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialized;

@Entity
public abstract class Graph implements JSONable {
	@Id Long id;

	Key<Student> student;
	@Serialized Query query;

	String title;
	GraphType type;

	protected Graph() {}

	public Graph(String title, Student student, Query query, GraphType type) {
		setStudent(student);
		this.query = query;

		this.title = title;
		this.type = type;
	}

	public Groupable<Activity> getQueryResult() {
		//return Registry.activityFinder().findByStudent(getStudent());
		return query.get();
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

	public GraphType getType() {
		return this.type;
	}

	public void setGraphType(GraphType type) {
		this.type = type;
	}
	
	public abstract GraphData getData();
	
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", getId());
		//json.put("student", getStudent().getId());
		json.put("title", getTitle());
		json.put("graphtype", getType().highchartsForm());
		return json;
	}

}

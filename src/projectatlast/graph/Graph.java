package projectatlast.graph;

import projectatlast.data.JSONable;
import projectatlast.data.Registry;
import projectatlast.group.Groupable;
import projectatlast.query.Query;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import javax.persistence.Id;

import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialized;

@Entity
public abstract class Graph implements JSONable {

	@Id Long id;
	Key<Student> student;
	String title;
	GraphType type;
	@Serialized Query query;

	protected Graph() {}

	public Graph(String title, Student student, Query query, GraphType type) {
		setStudent(student);
		this.query = query;

		this.title = title;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return Registry.studentFinder().getStudent(this.student);
	}

	public void setStudent(Student student) {
		this.student = Registry.studentFinder().getKey(student);
	}

	public void setStudent(Key<Student> student) {
		this.student = student;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Groupable<Activity> getQueryResult() {
		return query.get();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public GraphType getType() {
		return this.type;
	}

	public void setType(GraphType type) {
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

package projectatlast.graph;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.*;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class GraphFinder extends Finder {

	public GraphFinder(DAO dao) {
		super(dao);
	}

	public Graph getGraph(Key<Graph> key) {
		if (key == null)
			return null;
		return dao.ofy().get(key);
	}

	public Graph getGraph(long graphId) {
		return getGraph(getKey(graphId));
	}

	public Key<Graph> getKey(Graph graph) {
		if (graph == null)
			return null;
		return dao.key(graph);
	}

	public Key<Graph> getKey(long graphId) {
		if (graphId == 0)
			return null;
		return new Key<Graph>(Graph.class, graphId);
	}

	public List<Graph> findByStudent(Student student) {
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Graph> graphs = dao.begin().query(Graph.class)
				.filter("student =", studentKey).order("title");
		return graphs.list();
	}

	public boolean put(Graph graph) {
		if (graph == null)
			return false;
		dao.ofy().put(graph);
		return true;
	}

	public boolean remove(Key<Graph> key) {
		if (key == null)
			return false;
		dao.ofy().delete(key);
		return true;
	}

	public boolean remove(Graph graph) {
		if (graph == null)
			return false;
		dao.ofy().delete(graph);
		return true;
	}

}

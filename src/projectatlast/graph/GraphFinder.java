package projectatlast.graph;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

public class GraphFinder extends Finder {

	public GraphFinder(DAO dao) {
		super(dao);
		
	}

	public List<Graph> getGraphs(Student student){
		Key<Student> key = Registry.studentFinder().getKey(student);
		Query<Graph> graphs = dao.begin().query(Graph.class).filter("student =", key);
	
		return graphs.list();
		
	}
	
	public Key<Graph> putGraph(Graph graph){
		return dao.ofy().put(graph);
	}
	
	public Graph getGraph(Long id){
		return dao.ofy().query(Graph.class).filter("id =", id).limit(1).list().get(0);
	}
	
	public Key<Graph> getGraphKey(Long id){
		return dao.ofy().query(Graph.class).filter("id =", id).limit(1).getKey();
	}

	public void remove(Key<Graph> key) {
		// TODO Auto-generated method stub
		dao.ofy().delete(key);
	}
	public void remove(Graph graph) {
		// TODO Auto-generated method stub
		dao.ofy().delete(graph);
	}
	
}

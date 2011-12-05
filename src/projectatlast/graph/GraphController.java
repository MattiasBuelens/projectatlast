package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.milestone.ComparativeOperator;
import projectatlast.milestone.Milestone;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.Date;
import java.util.List;

/**
 * Use case controller for graphs.
 */

public class GraphController {

	public static Graph getGraph(long graphId, Student student) {
		Graph graph = Registry.graphFinder().getGraph(graphId);
		if(!verifyOwner(graph, student))
			return null;
		return graph;
	}

	public static boolean verifyOwner(Graph graph, Student student) {
		return graph.getStudent().equals(student);
	}

	public static boolean verifyOwner(long graphId, Student student) {
		Graph graph = Registry.graphFinder().getGraph(graphId);
		if (graph == null) {
			return false;
		}
		return verifyOwner(graph, student);
	}
	
	public static List<Graph> getAllFromStudent(Student student) {
		if (student == null)
			return null;
		return Registry.graphFinder().findByStudent(student);
	}
	
	protected static boolean put(Graph graph) {
		return Registry.graphFinder().put(graph);
	}
}

package projectatlast.graph;

import projectatlast.data.Registry;
import projectatlast.student.Student;

import java.util.*;

/**
 * Use case controller for graphs.
 */

public class GraphController {

	public static Graph getGraph(long graphId, Student student) {
		Graph graph = Registry.graphFinder().getGraph(graphId);
		if (!verifyOwner(graph, student))
			return null;
		return graph;
	}

	public static boolean verifyOwner(Graph graph, Student student) {
		if (graph == null) {
			return false;
		}
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
		if (student == null) {
			return null;
		}
		return Registry.graphFinder().findByStudent(student);
	}

	protected static boolean put(Collection<Graph> graphs) {
		return Registry.graphFinder().put(graphs);
	}

	protected static boolean put(Graph... graphs) {
		return Registry.graphFinder().put(graphs);
	}
}

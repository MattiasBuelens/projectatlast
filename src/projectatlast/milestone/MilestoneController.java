package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.Date;
import java.util.List;

/**
 * Use case controller for milestones.
 * 
 * @author Erik De Smedt
 */

public class MilestoneController {

	/**
	 * Creates a new milestone.
	 * 
	 * @param student
	 *            The student who creates the milestone.
	 * @param goal
	 *            The value the requested parameter needs to reach.
	 * @param deadline
	 *            The deadline for the milestone
	 * @param operator
	 *            The inequality you want to reach.
	 * @param query
	 *            The query that will be performed to fetch the data the
	 *            milestone will use
	 * @param queryParser
	 *            The query parser that will be used to retrieve the value of
	 *            the requested parameter.
	 * @param parseField
	 *            The parse field that will be used to retrieve a value for the
	 *            data
	 * 
	 * @return A boolean which is true if the creation of the milestone
	 *         succeeded.
	 */
	public static boolean createMilestone(Student student, long goal,
			Date deadline, Query query, Parser parser, ParseField parseField,
			ComparativeOperator operator) {
		// Calculate start value
		long startValue = calculateProgress(query, parser, parseField);
		//long startValue = 0;
		// Create milestone
		Milestone milestone = new Milestone(student, goal, startValue,
				deadline, operator, query, parser, parseField);
		// Put milestone
		//return Registry.milestoneFinder().put(milestone);
		return false;
	}

	/**
	 * Retrieves a list of all milestones from a given student.
	 * 
	 * @param student
	 *            The student.
	 * @return List of milestones from the student.
	 */
	public static List<Milestone> getMilestones(Student student) {
		return Registry.milestoneFinder().getMilestones(student);
	}

	public static long calculateProgress(Milestone milestone) {
		Query query = milestone.getQuery();
		Parser queryParser = milestone.getQueryParser();
		ParseField parseField = milestone.getParseField();
		return calculateProgress(query, queryParser, parseField);
	}

	public static long calculateProgress(Query query, Parser queryParser,
			ParseField parseField) {
		// Run query
		List<Activity> results = query.get().getValues();
		// Parse activities
		return queryParser.parse(results, parseField);
	}
}

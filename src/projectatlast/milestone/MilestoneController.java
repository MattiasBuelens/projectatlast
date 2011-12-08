package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.Date;
import java.util.List;

/**
 * Use case controller for milestones.
 */

public class MilestoneController {
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

	public static boolean verifyOwner(Milestone milestone, Student student) {
		return milestone.getStudent().equals(student);
	}

	public static boolean verifyOwner(long milestoneId, Student student) {
		Milestone milestone = Registry.milestoneFinder()
				.getMilestone(milestoneId);
		if (milestone == null) {
			return false;
		}
		return verifyOwner(milestone, student);
	}

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
	public static boolean createMilestone(Student student, double goal,
			Date deadline, Query query, Parser parser, ParseField parseField,
			ComparativeOperator operator) {
		// Calculate start value
		double startValue = calculateProgress(query, parser, parseField);
		// Create milestone
		Milestone milestone = new Milestone(student, goal, startValue,
				deadline, operator, query, parser, parseField);
		// Sentence
		milestone.setSentence(buildSentence(milestone));
		// Put milestone
		return Registry.milestoneFinder().put(milestone);
	}

	public static double calculateProgress(Milestone milestone) {
		milestone.isCompleted();
		Query query = milestone.getQuery();
		Parser queryParser = milestone.getQueryParser();
		ParseField parseField = milestone.getParseField();
		return calculateProgress(query, queryParser, parseField);
	}

	public static double calculateProgress(Query query, Parser queryParser,
			ParseField parseField) {
		// Run query
		List<Activity> results = query.get().getValues();
		// Parse activities
		return queryParser.parse(results, parseField);
	}

	public static String buildSentence(Milestone milestone) {
		return new Sentence(milestone).build();
	}

	protected static boolean put(Milestone milestone) {
		return Registry.milestoneFinder().put(milestone);
	}
}

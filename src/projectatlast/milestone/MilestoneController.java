package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.*;

/**
 * Use case controller for milestones.
 * 
 * <p>
 * This class handles all business logic regarding milestones. All servlets
 * which need to work with milestones need to call the methods in this class.
 * </p>
 */

public class MilestoneController {
	/**
	 * Retrieve all milestones from a given student grouped by their status.
	 * 
	 * <p>
	 * If no milestones where found, an empty list is returned.
	 * </p>
	 * 
	 * @param student
	 *            The student.
	 * @return Map of milestones from the student grouped by their status.
	 */
	public static Map<String, List<Milestone>> getMilestones(Student student) {
		if (student == null)
			return null;

		// Milestones to be updated
		List<Milestone> updateMilestones = new ArrayList<Milestone>();

		// Results map
		Map<String, List<Milestone>> results = new LinkedHashMap<String, List<Milestone>>();
		results.put("running", new ArrayList<Milestone>());
		results.put("completed", new ArrayList<Milestone>());
		results.put("failed", new ArrayList<Milestone>());

		// Get milestones
		List<Milestone> milestones = Registry.milestoneFinder()
				.getMilestones(student);

		for (Milestone milestone : milestones) {
			String group = "failed";

			// Check status
			if (!milestone.isExpired()) {
				group = "running";
				// Calculate progress and update
				milestone.setProgress(calculateProgress(milestone));
				updateMilestones.add(milestone);
			} else if (milestone.isCompleted()) {
				// Already completed
				group = "completed";
			}

			// Store in results
			results.get(group).add(milestone);
		}
		return results;
	}

	/**
	 * Verify that a milestone is owned by a given student.
	 * 
	 * <p>
	 * This controller should be called before any processing is done on a
	 * milestone.
	 * </p>
	 * 
	 * @param milestone
	 *            The milestone to be verified.
	 * @param student
	 *            The student who should be the owner.
	 * @return True if the student is the rightful owner of the milestone, false
	 *         if he is not.
	 */
	public static boolean verifyOwner(Milestone milestone, Student student) {
		return milestone.getStudent().equals(student);
	}

	/**
	 * Verify that a milestone is owned by a given student.
	 * 
	 * @param milestoneId
	 *            The identifier of the milestone to be verified.
	 * @param student
	 *            The student which should be the owner.
	 * @return True if the student is the rightful owner of the milestone, false
	 *         if he is not.
	 * @see #verifyOwner(Milestone, Student)
	 */
	public static boolean verifyOwner(long milestoneId, Student student) {
		Milestone milestone = Registry.milestoneFinder()
				.getMilestone(milestoneId);
		if (milestone == null) {
			return false;
		}
		return verifyOwner(milestone, student);
	}

	/**
	 * Create a new milestone and persist it.
	 * 
	 * <p>
	 * This controller does the following:
	 * </p>
	 * <ol>
	 * <li>A new milestone is created for the given student with the settings.</li>
	 * <li>The start value for the milestone is calculated using
	 * {@link #calculateProgress(Milestone)}.</li>
	 * <li>A readable title is created as title for the milestone.</li>
	 * <li>The milestone is persisted.</li>
	 * </ol>
	 * 
	 * <p>
	 * The requested parameter consists of a {@link Parser} and a
	 * {@link ParseField}. The parse field specifies which field is selected
	 * from each activity, the parser specifies how these values should be
	 * combined. For example, a milestone which checks the total duration of a
	 * range of activities would have a {@link Parser.SUM} parser using the
	 * {@link ParseField.DURATION}.
	 * </p>
	 * 
	 * <p>
	 * This method does not perform any validation checks, the caller is
	 * responsible for sanitizing any user-input values.
	 * </p>
	 * 
	 * @param student
	 *            The student who creates the milestone.
	 * @param goal
	 *            The value which needs to be reached to complete this
	 *            milestone.
	 * @param deadline
	 *            The deadline for the milestone.
	 * @param query
	 *            The query that will be executed to fetch the data.
	 * @param parser
	 *            The parser that will be used to calculate the value of the
	 *            requested parameter.
	 * @param parseField
	 *            The parse field that will be used to retrieve values for the
	 *            requested parameter.
	 * @param operator
	 *            The operator to use for comparing the progress with the goal.
	 * 
	 * @return True if the milestone was created and persisted successfully,
	 *         false otherwise.
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

	/**
	 * Calculate the progress of a milestone.
	 * 
	 * @param milestone
	 *            The milestone.
	 * @return The calculated progress.
	 */
	public static double calculateProgress(Milestone milestone) {
		// Calculate progress with milestone settings
		Query query = milestone.getQuery();
		Parser queryParser = milestone.getParser();
		ParseField parseField = milestone.getParseField();
		return calculateProgress(query, queryParser, parseField);
	}

	/**
	 * Calculate the progress of a query for a given parser and parse field.
	 * 
	 * @param query
	 *            The query that will be executed to fetch the data.
	 * @param parser
	 *            The parser that will be used to calculate the value of the
	 *            requested parameter.
	 * @param parseField
	 *            The parse field that will be used to retrieve values for the
	 *            requested parameter.
	 * @return The calculated progress.
	 * @see {@link #createMilestone}
	 */
	public static double calculateProgress(Query query, Parser parser,
			ParseField parseField) {
		// Run query
		List<Activity> results = query.get().getValues();
		// Parse activities
		return parser.parse(results, parseField);
	}

	/**
	 * Build a human-readable sentence to use as a milestone title.
	 * 
	 * @param milestone
	 *            The milestone.
	 * @return The sentence.
	 */
	public static String buildSentence(Milestone milestone) {
		return new SentenceBuilder(milestone).build();
	}

	/**
	 * Persist a milestone.
	 * 
	 * @param milestone
	 *            The milestone.
	 * @return True if the milestone was persisted successfully, false
	 *         otherwise.
	 */
	protected static boolean put(Milestone milestone) {
		return Registry.milestoneFinder().put(milestone);
	}
}

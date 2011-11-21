package projectatlast.milestone;

import projectatlast.student.Student;

import projectatlast.data.Registry;
import projectatlast.milestone.*;

import projectatlast.query.Query;
import projectatlast.query.Parser;
import projectatlast.query.ParseField;

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
	 *            The query parser that will be used to retrieve the value of the
	 *            requested parameter.
	 * @param parseField
	 *            The parse field that will be used to retrieve a value for the
	 *            data
	 * 
	 * @return A boolean which is true if the creation of the milestone
	 *         succeeded.
	 */
	public static boolean createMilestone(Student student, long goal, Date deadline,
			ComparativeOperator operator, Query query, Parser queryParser,
			ParseField parseField) {

		Milestone milestone = new Milestone(student, goal, deadline, operator,
				query, queryParser, parseField);
		return Registry.milestoneFinder().put(milestone);
	}

	/**
	 * Retrieves all milestones for a given user.
	 * 
	 * @param student
	 *            The student for which the milestones are requested
	 * @return A list containing all milestones of a given student
	 */
	public static List<Milestone> getMilestones(Student student) {
		return Registry.milestoneFinder().getMilestones(student);
	}
}

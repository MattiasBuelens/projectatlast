package projectatlast.milestone;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import projectatlast.student.Student;

import java.util.ArrayList;
import java.util.List;

public class MilestoneFinder extends Finder {

	public MilestoneFinder(DAO dao) {
		super(dao);
	}

	/**
	 * Retrieves a list of all milestones of a given Student.
	 * 
	 * @param student
	 * @return A list that contains all milestones of that single user.
	 */
	public List<Milestone> getMilestones(Student student) {
		ArrayList<Milestone> milestones = new ArrayList<Milestone>();
		// TODO Implement
		return milestones;
	}

	/**
	 * Saves a milestone to the datastore
	 * 
	 * @param milestone
	 *            The milestone that should be saved in the datastore.
	 * @return A boolean which is true if the operation succeeded
	 */
	public boolean put(Milestone milestone) {
		if (milestone == null)
			return false;
		dao.ofy().put(milestone);
		return true;
	}

}

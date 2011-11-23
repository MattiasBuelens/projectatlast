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
	 * Retrieves a list of all milestones from a given student.
	 * 
	 * @param student
	 *            The student.
	 * @return List of milestones from the student.
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
	 *            The milestone to be saved.
	 * @return true if successful, false otherwise.
	 */
	public boolean put(Milestone milestone) {
		if (milestone == null)
			return false;
		dao.ofy().put(milestone);
		return true;
	}

	/**
	 * Removes a milestone to the datastore
	 * 
	 * @param milestone
	 *            The milestone to be removed.
	 * @return true if successful, false otherwise.
	 */
	public boolean remove(Milestone milestone) {
		if (milestone == null)
			return false;
		dao.ofy().delete(milestone);
		return true;
	}

}

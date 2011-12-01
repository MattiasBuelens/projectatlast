package projectatlast.milestone;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

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
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Milestone> q = dao.ofy().query(Milestone.class);
		q.filter("student =", student);
		q.order("deadline");
		return q.list();
	}
	
	/**
	 * Retrieves a list of incomplete running milestones from a given student.
	 * 
	 * @param student
	 *            The student.
	 * @return List of running milestones from the student.
	 */
	public List<Milestone> getRunningMilestones(Student student) {
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Milestone> q = dao.ofy().query(Milestone.class);
		q.filter("student =", student);
		q.filter("deadline >=", new Date()).order("deadline");
		q.filter("completed =", false);
		return q.list();
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

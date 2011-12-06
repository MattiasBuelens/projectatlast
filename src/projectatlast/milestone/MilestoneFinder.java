package projectatlast.milestone;

import projectatlast.data.*;
import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.*;

public class MilestoneFinder extends Finder {

	public MilestoneFinder(DAO dao) {
		super(dao);
	}

	public Milestone getMilestone(Key<Milestone> key) {
		if (key == null)
			return null;
		try {
			return dao.begin().get(key);
		} catch (NotFoundException e) {
			return null;
		}
	}
	
	public Milestone getMilestone(long milestoneId) {
		return getMilestone(getKey(milestoneId));
	}

	public Key<Milestone> getKey(Milestone milestone) {
		if (milestone == null)
			return null;
		return dao.key(milestone);
	}

	public Key<Milestone> getKey(long milestoneId) {
		if (milestoneId <= 0)
			return null;
		return new Key<Milestone>(Milestone.class, milestoneId);
	}

	/**
	 * Retrieve a list of all milestones from a given student.
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
	 * Retrieve a list of running milestones from a given student. 
	 * Running means that the deadline has not passed already.
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
		return q.list();
	}
	
	public List<Milestone> getCompletedMilestones(Student student){
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Milestone> q = dao.ofy().query(Milestone.class);
		q.filter("student =", student);
		q.filter("deadline <=", new Date());
		q.filter("isCompleted =", true);
		return q.list();
	}
	
	public List<Milestone> getFailedMilestones(Student student){
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if (studentKey == null)
			return Collections.emptyList();
		Query<Milestone> q = dao.ofy().query(Milestone.class);
		q.filter("student =", student);
		q.filter("deadline <=", new Date());
		q.filter("isCompleted =", false);
		return q.list();
	}
	
	public boolean updateCompletion(Student student){
		Key<Student> studentKey = Registry.studentFinder().getKey(student);
		if(studentKey == null)
			return false;
		
		Query<Milestone> q = dao.ofy().query(Milestone.class);
		q.filter("student =", student);
		q.filter("isCompleted =", false);
		
		List<Milestone> milestones = q.list();
		for(Milestone milestone: milestones){
			milestone.isCompleted();
			put(milestone);
		}
		return true;
	}
	
	/**
	 * Persists a milestone.
	 * 
	 * @param milestone
	 *            The milestone.
	 * @return true if successful, false otherwise.
	 */
	public boolean put(Milestone milestone) {
		if (milestone == null)
			return false;
		dao.ofy().put(milestone);
		return true;
	}

	/**
	 * Remove a milestone.
	 * 
	 * @param milestone
	 *            The milestone.
	 * @return True if successful, false otherwise.
	 */
	public boolean remove(Milestone milestone) {
		if (milestone == null)
			return false;
		dao.ofy().delete(milestone);
		return true;
	}
}

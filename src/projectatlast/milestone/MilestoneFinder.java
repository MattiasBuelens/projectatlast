package projectatlast.milestone;

import projectatlast.data.DAO;
import projectatlast.data.Finder;
import projectatlast.Student.student;

import java.util.ArrayList;


public class MilestoneFinder extends Finder {

	public MilestoneFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Retrieves a list of all milestones of a given Student.
	 * @param student
	 * @return 	A list that contains all milestones of that single user.
	 */
	public List<Milestone> getMilestones(Student student)
	{
		ArrayList<Milestone> milestones = new ArrayList<Milestone>();
		
		if()
		
	}
	
	/**
	 * Saves a milestone to the datastore
	 * 
	 * @param milestone		The milestone that should be saved in the datastore.
	 * @return				A boolean which is true if the operation succeeded
	 */
	public boolean put(Milestone milestone)
	{
		if(milestone==null)
			return false;
		
		dao.ofy().put(milestone);
		return true;
		
	}

}

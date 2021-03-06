package projectatlast.data;

import projectatlast.course.CourseFinder;
import projectatlast.course.StudyProgramFinder;
import projectatlast.milestone.MilestoneFinder;
import projectatlast.graph.GraphFinder;
import projectatlast.student.StudentFinder;
import projectatlast.tracking.ActivityFinder;

public class Registry {

	protected DAO dao;

	protected StudyProgramFinder studyProgramFinder;
	protected CourseFinder courseFinder;
	protected StudentFinder studentFinder;
	protected ActivityFinder activityFinder;
	protected MilestoneFinder milestoneFinder;
	protected GraphFinder graphFinder;

	private static Registry instance;

	protected Registry() {
		dao = new DAO();

		studyProgramFinder = new StudyProgramFinder(dao);
		courseFinder = new CourseFinder(dao);
		studentFinder = new StudentFinder(dao);
		activityFinder = new ActivityFinder(dao);
		milestoneFinder = new MilestoneFinder(dao);
		graphFinder = new GraphFinder(dao);
	}

	/**
	 * Get the shared database access object (DAO).
	 * 
	 * @return the database access object.
	 */
	protected static Registry getInstance() {
		if (instance == null) {
			instance = new Registry();
		}
		return instance;
	}

	public static DAO dao() {
		return getInstance().dao;
	}

	public static StudyProgramFinder studyProgramFinder() {
		return getInstance().studyProgramFinder;
	}

	public static CourseFinder courseFinder() {
		return getInstance().courseFinder;
	}

	public static StudentFinder studentFinder() {
		return getInstance().studentFinder;
	}

	public static ActivityFinder activityFinder() {
		return getInstance().activityFinder;
	}

	public static MilestoneFinder milestoneFinder() {
		return getInstance().milestoneFinder;
	}
	
	public static GraphFinder graphFinder() {
		return getInstance().graphFinder;
	}

}

package projectatlast.data;

public class Registry {

	protected static DAO dao;

	protected static StudyProgramFinder studyProgramFinder;
	protected static CourseFinder courseFinder;
	protected static StudentFinder studentFinder;
	protected static ActivityFinder activityFinder;
	protected static MilestoneFinder milestoneFinder;

	private static Registry instance;

	protected Registry() {
		dao = new DAO();

		studyProgramFinder = new StudyProgramFinder(dao);
		courseFinder = new CourseFinder(dao);
		studentFinder = new StudentFinder(dao);
		activityFinder = new ActivityFinder(dao);
		milestoneFinder = new MilestoneFinder(dao);
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

	public StudyProgramFinder studyProgramFinder() {
		return getInstance().studyProgramFinder;
	}

	public CourseFinder courseFinder() {
		return getInstance().courseFinder;
	}

	public StudentFinder studentFinder() {
		return getInstance().studentFinder;
	}

	public ActivityFinder activityFinder() {
		return getInstance().activityFinder;
	}

	public MilestoneFinder milestoneFinder() {
		return getInstance().milestoneFinder;
	}

}

package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

public class StudyProgramFinder extends Finder {

	public StudyProgramFinder(DAO dao) {
		super(dao);
	}

	/**
	 * Get all available study programs.
	 * 
	 * @return list of study programs.
	 */
	public List<StudyProgram> getAll() {
		Collection<StudyProgram> programs = dao.ofy().get(StudyProgram.class)
				.values();
		if (programs.isEmpty()) {
			return Collections.emptyList();
		}
		return new ArrayList<StudyProgram>(programs);
	}

}

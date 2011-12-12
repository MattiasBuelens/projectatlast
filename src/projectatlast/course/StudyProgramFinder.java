package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;

public class StudyProgramFinder extends Finder {

	public StudyProgramFinder(DAO dao) {
		super(dao);
	}

	public StudyProgram getProgram(Key<StudyProgram> programKey) {
		if (programKey == null)
			return null;
		try {
			return dao.ofy().get(programKey);
		} catch(NotFoundException e) {
			return null;
		}
	}

	public StudyProgram getProgram(String programId) {
		return getProgram(getKey(programId));
	}

	public Key<StudyProgram> getKey(StudyProgram program) {
		if (program == null)
			return null;
		return dao.key(program);
	}

	public Key<StudyProgram> getKey(String programId) {
		if (programId == null || programId.isEmpty())
			return null;
		return new Key<StudyProgram>(StudyProgram.class, programId);
	}

	public Set<Key<StudyProgram>> getKeys(Iterable<StudyProgram> programs) {
		return dao.keys(programs);
	}

	/**
	 * Get all available study programs.
	 * 
	 * @return list of study programs.
	 */
	public List<StudyProgram> getPrograms() {
		return dao.ofy().query(StudyProgram.class).order("id").list();
	}

}
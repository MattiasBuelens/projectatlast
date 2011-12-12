package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;
import projectatlast.student.Student;

import java.util.*;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
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
		} catch (NotFoundException e) {
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
		// / Attempt to fetch from cache
		MemcacheService memcache = dao.memcache();
		String memKey = "studyPrograms";
		@SuppressWarnings("unchecked")
		List<StudyProgram> programs = (List<StudyProgram>) memcache.get(memKey);

		if (programs == null && !memcache.contains(memKey)) {
			// If no value or null value in cache, run query
			programs = dao.ofy().query(StudyProgram.class).order("id").list();
			// Cache result
			memcache.put(memKey, programs);
		}

		if (programs == null)
			programs = Collections.emptyList();

		return programs;
	}

}

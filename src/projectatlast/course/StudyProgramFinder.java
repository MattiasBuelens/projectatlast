package projectatlast.course;

import projectatlast.data.DAO;
import projectatlast.data.Finder;

import java.util.*;

import com.googlecode.objectify.Query;

public class StudyProgramFinder extends Finder {

	public StudyProgramFinder(DAO dao) {
		super(dao);
	}
	
	public List<StudyProgram> getAll(){
		//get alle studyprograms
		Map<Long,StudyProgram> sp = dao.ofy().get(StudyProgram.class);
		
		//get collection of all values
		Collection<StudyProgram> values = sp.values();
		
		ArrayList<StudyProgram> studyprograms = new ArrayList<StudyProgram>(values);
		
		return studyprograms;
	}

}

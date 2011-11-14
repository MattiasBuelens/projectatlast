package projectatlast.course;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class StudyProgram {

	List<Key<Course>> courses;
	String name;
	
	protected StudyProgram(){};
	
	public StudyProgram(String name,List<Key<Course>> courses){
		this.courses=courses;
		this.name=name;
	}

	public List<Key<Course>> getCourses(){
		return this.courses;
	}
}

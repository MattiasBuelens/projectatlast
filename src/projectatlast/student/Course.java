package projectatlast.student;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Course {
	@Id String id;
	String name;
	int credits;
	
	private Course() { }
}

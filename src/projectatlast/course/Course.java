package projectatlast.course;

import projectatlast.data.Registry;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;

@Entity
@Cached
public class Course {
	@Id String id;
	String name;
	int credits;
	
	protected Course() { }

	public Course(String id, String name, int credits) {
		this();
		this.id = id;
		this.name = name;
		this.credits = credits;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	@Override	
	public boolean equals(Object obj) {
	
		Course otherCourse = (Course)obj;
		return this.id.equals(otherCourse.id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	public String toString(){
		return this.name + Registry.courseFinder().getKey(this); 
	}
}

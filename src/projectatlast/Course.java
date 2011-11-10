package projectatlast;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Course {
	@Id String id;
	String name;
	int credits;

	public Course(String id, String name, int credits) {
		super();
		this.id = id;
		this.name = name;
		this.credits = credits;
	}

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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

}

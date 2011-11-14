package projectatlast.tracking;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Mood {

	@Id Long id;
	long comprehension;
	long interest;

	protected Mood() { }
	
	public Mood(long comprehension, long interest) {
		super();
		setComprehension(comprehension);
		setInterest(interest);
	}

	public long getComprehension() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getInterest() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setComprehension(long comprehension) {
		this.comprehension = comprehension;
	}

	public void setInterest(long interest) {
		this.interest = interest;
	}


	
	

}

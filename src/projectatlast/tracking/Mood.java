package projectatlast.tracking;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Mood {

	@Id Long id;

	protected Mood() { }

	public long getComprehension() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getInterest() {
		// TODO Auto-generated method stub
		return 0;
	}

}
package projectatlast;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class ActivitySlice {

	@Id Long id;
	@Parent Key<Activity> activity;

	Date date;
	long duration;

	protected ActivitySlice() {	}

}

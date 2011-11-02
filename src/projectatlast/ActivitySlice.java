package projectatlast;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class ActivitySlice {
	@Parent Key<Activity> activity;
	Date date;
	long duration;
	
	private ActivitySlice() { }
}

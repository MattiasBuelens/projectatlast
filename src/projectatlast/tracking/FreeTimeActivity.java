package projectatlast.tracking;

import projectatlast.student.Student;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class FreeTimeActivity extends Activity {

	protected FreeTimeActivity() {}

	public FreeTimeActivity(Student student, String type) {
		super(student, type);
	}

}

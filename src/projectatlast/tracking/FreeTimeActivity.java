package projectatlast.tracking;

import projectatlast.student.Student;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class FreeTimeActivity extends Activity {

	protected FreeTimeActivity() { }

	public FreeTimeActivity(Student student, String type) {
		super(student, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getTypes() {
		List<String> types = new ArrayList<String>();
		types.add("sport");
		types.add("gaming");
		return null;
	}

}

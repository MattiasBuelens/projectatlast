package projectatlast.tracking;

import projectatlast.student.Student;

import java.util.*;

import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class FreeTimeActivity extends Activity {

	protected FreeTimeActivity() { }

	public FreeTimeActivity(Student student, String type) {
		super(student, type);
	}
	
	@Override
	public String toString() {
		return super.toString() + "(" + getType() + ")";
	}

	public static Map<String, String> getTypes() {
		Map<String, String> types = new LinkedHashMap<String, String>();
		types.put("gaming", "Gaming");
		types.put("sports", "Sports");
		types.put("bar", "Bar");
		return types;
	}

}

package projectatlast;

public class StudyActivity extends Activity {

	private Course course;
	
	public StudyActivity(Course course){
		this.course=course;
	}
	public Course getCourse() {
		
		return this.course;
	}
	
	public String toString(){
		return course.getName();
	}

}

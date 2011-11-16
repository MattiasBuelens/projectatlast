package projectatlast.course;

import java.util.Comparator;

public class CourseNameComparator implements Comparator<Course> {

	@Override
	public int compare(Course o1, Course o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}

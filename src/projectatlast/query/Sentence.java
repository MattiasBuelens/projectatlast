package projectatlast.query;

import projectatlast.course.Course;
import projectatlast.tracking.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sentence {
	protected Query query;
	protected StringBuilder builder;

	protected Class<?> kind = Activity.class;
	protected Date startDate = null;
	protected Date stopDate = null;
	protected String type = "";
	protected String courseName = "";

	public Sentence(Query query) {
		this.query = query;
	}

	protected void parseOptions() {
		for (Option option : query.getOptions()) {
			if (option instanceof DateFilter) {
				DateFilter filter = (DateFilter) option;
				startDate = filter.from();
				stopDate = filter.to();
			} else if (option instanceof KindFilter) {
				kind = ((KindFilter) option).getKind();
			} else if (option instanceof CourseFilter) {
				Course course = ((CourseFilter) option).course();
				if (course != null)
					courseName = course.getName().toLowerCase();
			} else if (option instanceof TypeFilter) {
				type = ((TypeFilter) option).type();
			}
			parseOption(option);
		}
	}

	protected void parseOption(Option option) {}

	public String build() {
		// Parse options
		parseOptions();

		// Build sentence
		this.builder = new StringBuilder(512);
		intro();
		activity();
		dates();
		outro();
		return builder.toString();
	}

	protected void intro() {}

	protected void activity() {
		if (StudyActivity.class.isAssignableFrom(kind)) {
			studyActivity();
		} else if (FreeTimeActivity.class.isAssignableFrom(kind)) {
			freeTimeActivity();
		}
	}

	private void studyActivity() {
		studyType();
		studyCourse();
	}

	private void freeTimeActivity() {
		freeTimeType();
	}

	protected void activityType() {
		if (type != null && !type.isEmpty())
			builder.append(" on ").append(type);
	}

	protected void studyType() {
		activityType();
	}

	protected void studyCourse() {
		if (type == null || type.isEmpty()) {
			builder.append(" on ");
		} else {
			builder.append(" for ");
		}
		if (courseName.isEmpty()) {
			builder.append("all my courses");
		} else {
			builder.append(courseName);
		}
	}

	protected void freeTimeType() {
		builder.append(" on ");
		if (type == null || type.isEmpty()) {
			builder.append("all types of free time activities");
		} else {
			builder.append(type.toLowerCase());
		}
	}

	protected void dates() {
		dates("EEEE, d MMMM yyyy");
	}

	protected void dates(String dateFormat) {
		dates(new SimpleDateFormat(dateFormat, Locale.ENGLISH));
	}

	protected void dates(DateFormat dateFormat) {
		builder.append(" between ").append(dateFormat.format(startDate));
		builder.append(" and ").append(dateFormat.format(stopDate));
	}

	protected void outro() {
		builder.append(".");
	}
}

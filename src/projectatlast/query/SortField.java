package projectatlast.query;

import projectatlast.tracking.*;

public enum SortField {

	COURSE(StudyActivity.class),
	TYPE(Activity.class),
	DAY(ActivitySlice.class),
	DAY_OF_WEEK(ActivitySlice.class),
	HOUR(ActivitySlice.class),
	HOUR_OF_DAY(ActivitySlice.class);

	private Class<?> kind;

	private SortField(Class<?> kind) {
		this.kind = kind;
	}

	public Class<?> getKind() {
		return kind;
	}

	public Object getValue(Activity activity) {
		switch (this) {
		case COURSE:
			return ((StudyActivity) activity).getCourse();
		case TYPE:
			return activity.getType();
		}

		return null;
	}

}

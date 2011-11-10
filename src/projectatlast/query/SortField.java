package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.StudyActivity;

public enum SortField {

	COURSE, TYPE, DAY, DAY_OF_WEEK, HOUR, HOUR_OF_DAY;
	
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

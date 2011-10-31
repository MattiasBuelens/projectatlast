package projectatlast.query;

import projectatlast.Activity;
import projectatlast.StudyActivity;

public enum SortField {

	COURSE,
	TYPE,
	DAY,
	DAY_OF_WEEK,
	HOUR,
	HOUR_OF_DAY;
	
	public Object getValue(Activity activity){
		Object result = null;
		switch(this){
		case COURSE:
			result = ((StudyActivity)activity).getCourse();
			break;
		}
		return result;
	}
	
}

package projectatlast.query;

import projectatlast.tracking.*;

import java.util.Calendar;

public enum SortField {

	COURSE(StudyActivity.class) {
		@Override
		public Object getValue(Object object) {
			return ((StudyActivity) object).getCourse();
		}
	},
	TYPE(Activity.class) {
		@Override
		public Object getValue(Object object) {
			return ((Activity) object).getType();
		}
	},
	DAY(ActivitySlice.class) {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			// Clear fields
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			// Return date
			return cal.getTime();
		}
	},
	DAY_OF_WEEK(ActivitySlice.class) {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			// Get day of week from slice date
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			return cal.get(Calendar.DAY_OF_WEEK);
		}
	},
	HOUR(ActivitySlice.class) {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			// Clear fields
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			// Return date
			return cal.getTime();
		}
	},
	HOUR_OF_DAY(ActivitySlice.class) {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			// Get hour of day from slice date
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			return cal.get(Calendar.HOUR_OF_DAY);
		}
	};

	private Class<?> kind;

	private SortField(Class<?> kind) {
		this.kind = kind;
	}

	public Class<?> getKind() {
		return kind;
	}

	public boolean appliesTo(Class<?> cls) {
		return getKind().isAssignableFrom(cls);
	}

	public abstract Object getValue(Object object);
}

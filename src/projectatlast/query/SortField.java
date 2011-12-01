package projectatlast.query;

import projectatlast.tracking.*;

import java.util.Calendar;

public enum SortField {

	COURSE(StudyActivity.class, "Course") {
		@Override
		public Object getValue(Object object) {
			return ((StudyActivity) object).getCourse();
		}
	},
	TYPE(Activity.class, "Activity type") {
		@Override
		public Object getValue(Object object) {
			return ((Activity) object).getType();
		}
	},
	DAY(ActivitySlice.class, "Day") {
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
	DAY_OF_WEEK(ActivitySlice.class, "Day of the week") {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			// Get day of week from slice date
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			return cal.get(Calendar.DAY_OF_WEEK);
		}
	},
	HOUR(ActivitySlice.class, "Hour") {
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
	HOUR_OF_DAY(ActivitySlice.class, "Hour of the day") {
		@Override
		public Object getValue(Object object) {
			ActivitySlice slice = (ActivitySlice) object;
			// Get hour of day from slice date
			Calendar cal = Calendar.getInstance();
			cal.setTime(slice.getDate());
			return cal.get(Calendar.HOUR_OF_DAY);
		}
	},
	USEDTOOLS(StudyActivity.class, "Used tools") {
		@Override
		public Object getValue(Object object) {
			return ((StudyActivity) object).getUsedTools();
		}
	};

	private Class<?> kind;
	private String humanReadable;

	private SortField(Class<?> kind, String humanReadable) {
		this.kind = kind;
		this.humanReadable = humanReadable;
	}

	public Class<?> getKind() {
		return kind;
	}

	public boolean appliesTo(Class<?> cls) {
		return getKind().isAssignableFrom(cls);
	}
	
	/**
	 * Retrieve the human readable name of the parser.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/**
	 * Retrieve the identifier of the parser.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name().toLowerCase();
	}

	/**
	 * Retrieve the parser with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The parser.
	 */
	public static SortField fromId(String id) {
		return SortField.valueOf(id.toUpperCase());
	}

	public abstract Object getValue(Object object);
}

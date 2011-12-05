package projectatlast.group;

import projectatlast.course.Course;
import projectatlast.tracking.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public enum GroupField {

	COURSE(StudyActivity.class, "Course") {
		@Override
		public Object getValue(Object object) {
			return ((StudyActivity) object).getCourse();
		}

		@Override
		public String formatValue(Object object) {
			return ((Course) object).getName();
		}
	},
	TYPE(Activity.class, "Activity type") {
		@Override
		public Object getValue(Object object) {
			return ((Activity) object).getType();
		}
		
		@Override
		public String formatValue(Object object) {
			return object.toString();
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
		
		@Override
		public String formatValue(Object object) {
			Date date = (Date)object;
			return new SimpleDateFormat("d MMMM").format(date);
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
		
		@Override
		public String formatValue(Object object) {
			String[] weekDays = new SimpleDateFormat().getDateFormatSymbols().getWeekdays();
			return weekDays[((Integer)object).intValue()];
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
		
		@Override
		public String formatValue(Object object) {
			Date date = (Date)object;
			return new SimpleDateFormat("HH:mm\nd MMMM").format(date);
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
		
		@Override
		public String formatValue(Object object) {
			return object.toString() + ":00";
		}
	},
	USEDTOOLS(StudyActivity.class, "Used tools") {
		@Override
		public Object getValue(Object object) {
			return ((StudyActivity) object).getUsedTools();
		}
		
		@Override
		public String formatValue(Object object) {
			return object.toString();
		}
	};

	private Class<?> kind;
	private String humanReadable;

	private GroupField(Class<?> kind, String humanReadable) {
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
	 * Retrieve the human readable name of the sort field.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/**
	 * Retrieve the identifier of the sort field.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name().toLowerCase();
	}

	/**
	 * Retrieve the sort field with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The sort field.
	 */
	public static GroupField fromId(String id) {
		return GroupField.valueOf(id.toUpperCase());
	}

	public abstract Object getValue(Object object);

	public abstract String formatValue(Object object);
}

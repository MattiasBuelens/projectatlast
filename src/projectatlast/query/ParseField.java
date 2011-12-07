/**
 * @author thomas
 * 
 */
package projectatlast.query;

import projectatlast.tracking.Activity;

public enum ParseField {

	DURATION("duration", "milliseconds") {
		@Override
		public long getValue(Activity activity) {
			return activity.getDuration();
		}
	},
	MOOD_MODULUS("overall mood", "points") {
		@Override
		public long getValue(Activity activity) {
			return (long) Math.sqrt(Math.pow(activity.getMood().getComprehension(),2)+Math.pow(activity.getMood().getInterest(),2));
		}
	},
	MOOD_INTEREST("mood interest", "points") {
		@Override
		public long getValue(Activity activity) {
			return activity.getMood().getInterest();
		}
	},
	MOOD_COMPREHENSION("mood comprehension", "points") {
		@Override
		public long getValue(Activity activity) {
			return activity.getMood().getComprehension();
		}
	};

	private String humanReadable;
	private String unit;

	private ParseField(String humanReadable, String unit) {
		this.humanReadable = humanReadable;
		this.unit = unit;
	}

	public abstract long getValue(Activity activity);

	/**
	 * Retrieve the human readable name of the parse field.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}
	
	/**
	 * Retrieve the unit of the parse field.
	 * 
	 * @return The name of the unit.
	 */
	public String unit() {
		return unit;
	}

	/**
	 * Retrieve the identifier of the parse field.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name().toLowerCase();
	}

	/**
	 * Retrieve the parse field with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The parse field.
	 */
	public static ParseField fromId(String id) {
		return ParseField.valueOf(id.toUpperCase());
	}
}

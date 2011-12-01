/**
 * @author thomas
 * 
 */
package projectatlast.query;

import projectatlast.tracking.Activity;

public enum ParseField {

	DURATION("duration") {
		@Override
		public long getValue(Activity activity) {
			return activity.getDuration();
		}
	},
	MOOD_INTEREST("mood interest") {
		@Override
		public long getValue(Activity activity) {
			return activity.getMood().getInterest();
		}
	},
	MOOD_COMPREHENSION("mood comprehension") {
		@Override
		public long getValue(Activity activity) {
			return activity.getMood().getComprehension();
		}
	};

	private String humanReadable;

	private ParseField(String humanReadable) {
		this.humanReadable = humanReadable;
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
	 * Retrieve the identifier of the parse field.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name();
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

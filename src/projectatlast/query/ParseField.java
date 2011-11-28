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

	/**
	 * Retrieve the human readable name of the parse field.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/**
	 * Retrieve the name of the parse field.
	 * 
	 * @return The name.
	 */
	public String id() {
		return this.name();
	}

	public abstract long getValue(Activity activity);
}

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
	 * Returns a human readable form of the parse field.
	 * 
	 * @return The human readable string.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/*public String id() {
		return this.name();
	}*/
	
	public int id() {
		return this.ordinal();
	}
}

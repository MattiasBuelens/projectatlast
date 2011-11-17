/**
 * @author thomas
 * 
 */
package projectatlast.query;

import projectatlast.tracking.Activity;

public enum ParseField {

	DURATION, MOOD_INTEREST, MOOD_COMPREHENSION;

	public long getValue(Activity activity) {
		long result = 0;
		switch (this) {
		case DURATION:
			result = activity.getDuration();
			break;
		case MOOD_INTEREST:
			result = activity.getMood().getInterest();
			break;
		case MOOD_COMPREHENSION:
			result = activity.getMood().getComprehension();
			break;
		}

		return result;
	}
	
	/**
	 * Returns a human readable form of the enumeration
	 * @return
	 */
	public String humanReadable() {
		String result = "";
		switch (this) {
		case DURATION:
			result =  "duration";
			break;
		case MOOD_INTEREST:
			result =  "mood interest";
			break;
		case MOOD_COMPREHENSION:
			result =  "mood comprehension";
			break;
		}

		return result;
	}
	
	/**
	 * Returns the id number of the parsefield, this can be used
	 * for example in html forms to uniquely identify a parsefield.
	 * @return
	 */
	public int id() {
		return this.ordinal();
	}
}

package projectatlast.query;

import projectatlast.Activity;

public enum ParseField {

	DURATION, MOOD_INTEREST, MOOD_COMPREHENSION;

	public long getValue(Activity activity) {
		switch (this) {
		case DURATION:
			return activity.getDuration();
		case MOOD_INTEREST:
			return activity.getMood().getInterest();
		case MOOD_COMPREHENSION:
			return activity.getMood().getComprehension();
		}

		return 0;
	}
}

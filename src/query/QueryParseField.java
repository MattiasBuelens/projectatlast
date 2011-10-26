package query;

import projectatlast.Activity;

public enum QueryParseField {

	DURATION,
	MOOD_INTEREST,
	MOOD_COMPREHENSION;
	
	public long getValue(Activity activity){
		long result = 0;
		switch(this){
		case DURATION:
			result=(long)activity.getDuration();
			break;
		case MOOD_INTEREST:
			result=(long)activity.getMood().getInterest();
			break;
		case MOOD_COMPREHENSION:
			result=(long)activity.getMood().getComprehension();
		}
		
		return result;
	}
}

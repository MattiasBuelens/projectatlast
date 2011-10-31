package query;

import java.util.List;

import projectatlast.Activity;

public enum Parser {

	MAX,
	MIN,
	SUM,
	AVG;
	
	public long parse(List<Activity> activities, ParseField parseField){
		
		long result = 0;
		
		switch(this){ 
		case MAX:
			result = new ParseSum().parse(activities, parseField);
			break;
		case MIN:
			result = new ParseMin().parse(activities, parseField);
			break;
		case SUM:
			result = new ParseSum().parse(activities, parseField);
			break;
		case AVG:
			result = new ParseAvg().parse(activities, parseField);
		}
		
		return result;
	}
	
}

package query;

import java.util.List;

import projectatlast.Activity;

public enum QueryParser {

	MAX,
	MIN,
	SUM,
	AVG;
	
	public long parse(List<Activity> activities, QueryParseField parseField){
		
		long result =0;
		
		switch(this){
		case MAX:
			result = new QueryParseSum().parse(activities, parseField);
			break;
		case MIN:
			result = new QueryParseMin().parse(activities, parseField);
			break;
		case SUM:
			result = new QueryParseSum().parse(activities, parseField);
			break;
		case AVG:
			result = new QueryParseAvg().parse(activities, parseField);
		}
		
		return result;
	}
	
}

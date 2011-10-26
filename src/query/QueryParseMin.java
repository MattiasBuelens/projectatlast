package query;

import java.util.List;

import projectatlast.Activity;

public class QueryParseMin implements QueryParseMethod {

	@Override
	public long parse(List<Activity> activities, QueryParseField parseField) {
		
		return calculateMin(activities,parseField);
	}
	
	private long calculateMin(List<Activity> activities,QueryParseField parseField){
			long min = parseField.getValue(activities.get(0));
			for(int i=0;i<activities.size();i++){
				min += Math.min(min,parseField.getValue(activities.get(i)));
			}
	return min;
	}

}

package query;

import java.util.List;

import projectatlast.Activity;

public class QueryParseMax implements QueryParseMethod {

	@Override
	public long parse(List<Activity> activities, QueryParseField parseField) {
		
		long max = calculateMax(activities,parseField);
		return max;
	}
	
	private long calculateMax(List<Activity> activities,QueryParseField parseField){
			long max = parseField.getValue(activities.get(0));
			for(int i=0;i<activities.size();i++){
				max += Math.max(max,parseField.getValue(activities.get(i)));
			}
	return max;
	}

	
}

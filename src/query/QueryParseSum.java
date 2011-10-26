package query;

import java.util.List;

import projectatlast.Activity;

public class QueryParseSum implements QueryParseMethod {

	@Override
	public long parse(List<Activity> activities, QueryParseField parseField) {
		
		
		return calculateSum(activities,parseField);
	}
	
	private long calculateSum(List<Activity> activities, QueryParseField parseField) {
		long sum = 0;
		for(int i=0;i<activities.size();i++){
			sum += parseField.getValue(activities.get(i));
		}
		return sum;
	}

}

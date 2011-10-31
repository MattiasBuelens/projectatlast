package projectatlast.query;

import java.util.List;

import projectatlast.Activity;

public class ParseSum implements ParseMethod {

	@Override
	public long parse(List<Activity> activities, ParseField parseField) {
		
		
		return calculateSum(activities,parseField);
	}
	
	private long calculateSum(List<Activity> activities, ParseField parseField) {
		long sum = 0;
		for(int i=0;i<activities.size();i++){
			sum += parseField.getValue(activities.get(i));
		}
		return sum;
	}

}

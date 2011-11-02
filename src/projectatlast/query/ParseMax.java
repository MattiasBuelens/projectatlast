package projectatlast.query;

import projectatlast.Activity;

import java.util.List;

public class ParseMax implements ParseMethod {

	@Override
	public long parse(List<Activity> activities, ParseField parseField) {
		
		
		return calculateMax(activities,parseField);
	}
	
	private long calculateMax(List<Activity> activities,ParseField parseField){
			long max = parseField.getValue(activities.get(0));
			for(int i=0;i<activities.size();i++){
				max = Math.max(max,parseField.getValue(activities.get(i)));
			}
	return max;
	}

	
}

package projectatlast.query;

import java.util.List;

import projectatlast.Activity;

public class ParseMin implements ParseMethod {

	@Override
	public long parse(List<Activity> activities, ParseField parseField) {
		
		return calculateMin(activities,parseField);
	}
	
	private long calculateMin(List<Activity> activities,ParseField parseField){
			long min = parseField.getValue(activities.get(0));
			for(int i=0;i<activities.size();i++){
				min = Math.min(min,parseField.getValue(activities.get(i)));
			}
	return min;
	}

}

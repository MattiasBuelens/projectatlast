package projectatlast.query;

import projectatlast.Activity;

import java.util.List;

public class ParseAvg implements ParseMethod {


	@Override
	public long parse(List<Activity> activities, ParseField parseField) {
		
		return calculateAvg(activities,parseField);
	}
	
	private long calculateAvg(List<Activity> activities,ParseField parseField){
			long sum = Parser.SUM.parse(activities, parseField);
			long amount = activities.size();
			
			long avg = sum/amount;
			return avg;
	}

}
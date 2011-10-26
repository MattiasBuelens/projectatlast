package query;

import java.util.List;

import projectatlast.Activity;

public class QueryParseAvg implements QueryParseMethod {


	@Override
	public long parse(List<Activity> activities, QueryParseField parseField) {
		
		return calculateAvg(activities,parseField);
	}
	
	private long calculateAvg(List<Activity> activities,QueryParseField parseField){
			long sum = QueryParser.SUM.parse(activities, parseField);
			long amount = activities.size();
			
			long avg = sum/amount;
			return avg;
	}

}

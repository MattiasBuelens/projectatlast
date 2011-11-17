package projectatlast.query;

import projectatlast.tracking.Activity;

import java.util.List;

public enum Parser {

	MAX, MIN, SUM, AVG,JAJ;

	public long parse(List<Activity> activities, ParseField parseField) {

		long result = 0;

		switch (this) {
		case MAX:
			result = new ParseMax().parse(activities, parseField);
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

	public String humanReadable(){

		String result = "";

		switch (this) {
		case MAX:
			result = "maximum";
			break;
		case MIN:
			result = "minimum";
			break;
		case SUM:
			result = "sum";
			break;
		case AVG:
			result = "average";
			break;
		case JAJ:
			result = "jaj";
			break;
		}

		return result;
	}
	
	
	public int id(){
		return this.ordinal();
	}
	

}

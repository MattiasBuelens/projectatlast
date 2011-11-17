/**
 * ComparativeOperator
 * 
 * Enumeration of operators used to compare two values.
 * 
 * @version: 1.0
 * @author: Mattias Buelens en Thomas Goosens
 */

package projectatlast.milestone;

public enum ComparativeOperator {

	EQUALS, LESS_THAN, GREATER_THAN, LESS_THAN_OR_EQUAL, GREATER_THAN_OR_EQUAL;

	public boolean compare(long left, long right) {
		boolean result = false;

		switch (this) {
		case EQUALS:
			result = left == right;
			break;
		case LESS_THAN:
			result = left < right;
			break;
		case GREATER_THAN:
			result = left > right;
			break;
		case LESS_THAN_OR_EQUAL:
			result = left <= right;
			break;
		case GREATER_THAN_OR_EQUAL:
			result = left >= right;
			break;

		}

		return result;
	}
	
	
	public String humanReadable() {
		String result = "";

		switch (this) {
		case EQUALS:
			result = "equals";
			break;
		case LESS_THAN:
			result = "less than";
			break;
		case GREATER_THAN:
			result = "greater than";
			break;
		case LESS_THAN_OR_EQUAL:
			result = "less than or equal";
			break;
		case GREATER_THAN_OR_EQUAL:
			result = "greater than or equal";
			break;

		}

		return result;
	}
	
	public int id() {

		return this.ordinal();
	}
	
	
	/*
	public int completion(long initial, long variable, long goal){
		int completion =0;
		switch (this) {
		case EQUALS:
			completion = 100 -(int) (Math.abs((variable - goal))*100/Math.abs((goal-initial)));
			if(completion > 100 || this.compare(variable,goal)){
				completion = 100;
			}
			break;
		case LESS_THAN:
			completion = 100 - (int) (Math.abs((variable - goal))*100/Math.abs((goal-initial)));
			if(this.compare(variable,goal)){
				completion = 100;
			}
			break;
		case GREATER_THAN:
			completion = 100- (int) (Math.abs((variable - goal))*100/Math.abs((goal-initial)));
			if(this.compare(variable,goal)){
				completion = 100;
			}
			break;
		case LESS_THAN_OR_EQUAL:
			completion = (int) (Math.abs((variable - goal))*100/Math.abs((goal-initial)));
			if(completion > 100 || this.compare(variable,goal)){
				completion = 100;
			}
			break;
		case GREATER_THAN_OR_EQUAL:
			completion = (int) (Math.abs((variable - goal))*100/Math.abs((goal-initial)));
			if(completion > 100 || this.compare(variable,goal)){
				completion = 100;
			}
			break;
		}
		return completion;
	}
	*/
	
	public String toString(){
		return this.humanReadable();
	}
	
	
	

	

}

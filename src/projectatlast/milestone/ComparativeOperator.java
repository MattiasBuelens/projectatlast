/**
 * version: final
 * Author: Mathias Beulens en Thomas Goosens
 * 
 * 
 * This is an enumeration containing 5 comparative operators.
 */

package projectatlast.milestone;

public enum ComparativeOperator {

	EQUALS,
	LESS_THAN,
	GREATER_THAN,
	LESS_THAN_OR_EQUAL,
	GREATER_THAN_OR_EQUAL;
	
	public boolean compare(long left, long right){
		boolean result = false;
		
		switch(this){
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
}

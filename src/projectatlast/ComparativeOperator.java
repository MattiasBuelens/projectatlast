package projectatlast;

public enum ComparativeOperator {

	EQUALS,
	LESS_THAN,
	GREATER_THAN,
	LESS_THAN_OR_EQUAL,
	GREATER_THAN_OR_EQUAL;
	
	public boolean compare(long value1, long value2){
		boolean result = false;
		
		switch(this){
		case EQUALS:
			result = value1==value2;
			break;
		case LESS_THAN:
			result = value1<value2;
			break;
		case GREATER_THAN:
			result = value1>value2;
			break;
		case LESS_THAN_OR_EQUAL:
			result = value1<=value2;
			break;
		case GREATER_THAN_OR_EQUAL:
			result = value1>=value2;
			break;
		}
		
		return result;
	}
	
}

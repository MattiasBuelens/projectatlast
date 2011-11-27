package projectatlast.graph;

public enum GraphType {

	COLUMN,
	BAR,
	PIE;
	
	public String highchartsForm(){
		String result = "";
		switch(this){
		case COLUMN:
			result = "column";
			break;
		case BAR:
			result = "bar";
			break;
		case PIE:
			result ="pie";
			break;
		}
		
		return result;
	}
}

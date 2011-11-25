package projectatlast.graph;

import java.util.List;

public class XYData {

	List<Object> x; 
	List<Long> y;
	
	
	public XYData(List<Object> x, List<Long> y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public List<Object> getX(){
		
		return x;
	}
	
	public List<Long> getY(){
		return y;
	}
	
}

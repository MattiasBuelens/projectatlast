package projectatlast.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.types.resources.Sort;

import projectatlast.Activity;

public class Group {

	private SortField sortField;

	
	
	public Group(SortField sortField) {
		super();
		this.sortField = sortField;
	}


	public Map<Object,List<Activity>> group(List<Activity> activities){

		Map<Object, List<Activity>> grouped = new LinkedHashMap<Object, List<Activity>>();
		
		Iterator<Activity> it = activities.iterator();
		
		while(it.hasNext()){
			Activity activity = it.next();
			
			//the retrieved value from the activity
			Object value = sortField.getValue(activity);
			
			//does group with name 'value' exist?
			if(groupExists(grouped,value)){
				//edit the List<Activity>
				
				//get the list and add the activity
				grouped.get(value).add(activity);
			}else{
				//put new item -> create new group
				List<Activity> groupedActivities = new ArrayList<Activity>();
				groupedActivities.add(activity);
				grouped.put(value,groupedActivities );
			}
			
		}
		
		return grouped;

		
	}
	
	
private boolean groupExists(Map<Object, List<Activity>> map,Object key){
	
	boolean result = false;
	
	if(map.containsKey(key)){
		result=true;
	}
	
	
	return result;
}
	
}

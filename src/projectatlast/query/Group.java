package projectatlast.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import projectatlast.Activity;

public class Group {

	public Map<Object,List<Activity>> group(SortField sortField, List<Activity> activities){

		Map<Object, List<Activity>> grouped = new LinkedHashMap<Object, List<Activity>>();
		
		Iterator<Activity> it = activities.iterator();
		
		while(it.hasNext()){
			Activity activity = it.next();
			
			//the retrieved value from the activity
			Object value = sortField.getValue(activity);
			
			//does group with name 'value' exist?
			if(groupExists()){
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
	
	
private boolean groupExists(){
	
	boolean result = false;
	
	return result;
}
	
}

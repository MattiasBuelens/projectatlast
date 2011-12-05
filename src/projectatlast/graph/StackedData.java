package projectatlast.graph;

import projectatlast.group.Group;
import projectatlast.group.GroupField;
import projectatlast.tracking.Activity;

import java.util.*;

public class StackedData {

	List<Object> groups;
	
	
	/**
	 * A map of maps: every group has its own map with as key: the group
	 * Each map gives the activities belonging to the 
	 *  Map<Group , Map<Subgroup,parsedResult>> 
	 */
	Map<Object, Map<Object,Long>> stacks;
	
	public StackedData(StackedGraph graph){
		Group group = new Group(graph.sortField);
		Map<Object, List<Activity>> grouped = group.group(graph.getActivities());
		
		stacks = new HashMap<Object, Map<Object,Long>>();
		//set groups
		groups=new ArrayList<Object>(grouped.keySet());
		//iterate over groups
		for(Object g: groups){
			
			Map<Object ,List<Activity>> stacks_activities = new HashMap<Object, List<Activity>>();

			Group subgroup = new Group(graph.getSubgroup());
			Map<Object, List<Activity>> subgrouped =  subgroup.group(grouped.get(g));
			
			Map<Object, Long> parsedSubs = new HashMap<Object, Long>();
			//iterate over subgroups
			for(Object sub:subgrouped.keySet()){
				
				//use parser on every subgroup
				Long parsedResult = graph.parser.parse(subgrouped.get(sub),graph.parseField);
				parsedSubs.put(sub, parsedResult);
		
			
				
			}
			//add to stacks
			stacks.put(g, parsedSubs);
			
			
		}
		
	
		
		
	

	}
	
	
	public List<Object> getGroups(){
		return new ArrayList<Object>(stacks.keySet());
	}
	
	public List<Object> getSubGroups(Object group){
		return new ArrayList<Object>(stacks.get(group).keySet());
	}
	
	public Long getParsed(Object group, Object subGroup) {
		return stacks.get(group).get(subGroup);
	}


	public Collection<? extends Object> getSubGroups() {
		HashSet<Object> subgroups = new HashSet<Object>();
		for(Object group:getGroups()){
			for(Object subgroup:getSubGroups(group)){
				subgroups.add(subgroup);
			}
		}
		return subgroups;
	}


	public List<List<Long>> getResults() {
		List<List<Long>> result = new ArrayList<List<Long>>();
		for(Object group:getGroups()){
			List<Long> groupresults = new ArrayList<Long>();
			
			for(Object subgroup:getSubGroups()){
				groupresults.add(getParsed(group, subgroup));
			}
			
			result.add(groupresults);		
		}
		
		return result;
	}
	
}

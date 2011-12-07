package projectatlast.graph;

import projectatlast.group.Group;
import projectatlast.group.Grouped;

import java.util.*;

import com.google.appengine.repackaged.org.json.*;

public class StackedData extends GraphData {

	Grouped<Long> data;

	/**
	 * A map of maps: every group has its own map with as key: the group Each
	 * map gives the activities belonging to the Map<Group ,
	 * Map<Subgroup,parsedResult>>
	 */
	Map<Object, Map<Object, Long>> stacks;

	public StackedData(List<Group> groups, Grouped<Long> data) {
		super(groups);
		this.data = data;
	}

	public List<Object> getGroups() {
		return new ArrayList<Object>(data.getKeys(1));
	}

	public List<Object> getSubGroups() {
		return new ArrayList<Object>(data.getKeys(2));
	}

	/**
	 * Get all formatted group names from the primary group.
	 * 
	 * @return List of group names.
	 */
	public List<String> getGroupNames() {
		return formatGroupNames(groups.get(0), getGroups());
	}
	
	/**
	 * Get all formatted group names from the secondary group.
	 * 
	 * @return List of group names.
	 */
	public List<String> getSubGroupNames() {
		return formatGroupNames(groups.get(1), getSubGroups());
	}

	/**
	 * Get all results as a two-dimensional list of values.
	 * 
	 * @return List of list of values.
	 */
	protected List<List<Long>> getResults() {
		List<List<Long>> results = new ArrayList<List<Long>>();
		// Iterate over all groups
		for (Object groupKey : getGroups()) {
			List<Long> groupResults = new ArrayList<Long>();
			// Get group
			Grouped<Long> group = data.getChild(groupKey);
			if (group != null) {
				// Iterate over all sub groups
				for (Object subGroupKey : getSubGroups()) {
					Long subGroupResult = null;
					// Get sub group
					Grouped<Long> subGroup = group.getChild(subGroupKey);
					if (subGroup != null) {
						// Get first result
						List<Long> subGroupResults = subGroup.getValues();
						if (!subGroupResults.isEmpty()) {
							subGroupResult = subGroupResults.get(0);
						}
					}
					groupResults.add(subGroupResult);
				}
			}
			results.add(groupResults);
		}
		return results;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		return toJSON(json);
	}

	@Override
	public JSONObject toJSON(JSONObject json) throws JSONException {
		json.put("groups", new JSONArray(getGroupNames()));
		json.put("subgroups", new JSONArray(getSubGroupNames()));
		json.put("results", new JSONArray(getResults()));
		return json;
	}

}

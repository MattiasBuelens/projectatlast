package projectatlast.graph;

import projectatlast.group.*;
import projectatlast.tracking.Activity;

import java.util.*;

import com.google.appengine.repackaged.org.json.*;

public class StackedData implements GraphData {

	Grouped<Long> data;
	List<Group> groups;

	/**
	 * A map of maps: every group has its own map with as key: the group Each
	 * map gives the activities belonging to the Map<Group ,
	 * Map<Subgroup,parsedResult>>
	 */
	Map<Object, Map<Object, Long>> stacks;

	public StackedData(Grouped<Long> data, List<Group> groups) {
		this.data = data;
		this.groups = groups;
	}

	public List<Object> getGroups() {
		return new ArrayList<Object>(data.getKeys(1));
	}

	public List<Object> getSubGroups() {
		return new ArrayList<Object>(data.getKeys(2));
	}

	public List<List<String>> getGroupNames() {
		List<List<String>> names = new ArrayList<List<String>>();
		ListIterator<Group> it = groups.listIterator();
		while (it.hasNext()) {
			Group group = it.next();
			int groupIndex = it.nextIndex();
			List<String> groupNames = new ArrayList<String>();
			for (Object groupKey : data.getKeys(groupIndex)) {
				groupNames.add(group.getField().formatValue(groupKey));
			}
			names.add(groupNames);
		}
		return names;
	}

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
	public Grouped<Long> getData() {
		return data;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		return toJSON(json);
	}

	@Override
	public JSONObject toJSON(JSONObject json) throws JSONException {
		List<List<String>> groupNames = getGroupNames();
		json.put("groups", new JSONArray(groupNames.get(0)));
		json.put("subgroups", new JSONArray(groupNames.get(1)));
		json.put("results", new JSONArray(getResults()));
		return json;
	}

}

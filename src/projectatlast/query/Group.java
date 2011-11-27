package projectatlast.query;

import java.io.Serializable;
import java.util.*;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	SortField sortField;

	protected Group() {}

	public Group(SortField sortField) {
		this.sortField = sortField;
	}

	public Class<?> getKind() {
		return sortField.getKind();
	}

	public boolean appliesTo(Class<?> cls) {
		return sortField.appliesTo(cls);
	}

	/**
	 * Groups a list of objects.
	 * 
	 * @param objects
	 *            The list of objects to group.
	 * @return Map containing lists of objects mapped by their group key.
	 */
	public <T> Map<Object, List<T>> group(List<T> objects) {
		Map<Object, List<T>> grouped = new LinkedHashMap<Object, List<T>>();

		for (T object : objects) {
			// Get the group key for this object
			Object key = sortField.getValue(object);

			// Is there already a list for this key in the groups map?
			if (grouped.containsKey(key)) {
				// Add object to list
				grouped.get(key).add(object);
			} else {
				// Create new list and add to map
				List<T> groupNode = new ArrayList<T>();
				groupNode.add(object);
				grouped.put(key, groupNode);
			}
		}

		return grouped;
	}

}

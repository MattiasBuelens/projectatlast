package projectatlast.group;

import java.util.*;

/**
 * A branch in a grouped collection.
 */
public abstract class GroupBranch<T> implements Grouped<T> {

	protected Object key;
	
	public GroupBranch(Object key) {
		this.key = key;
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public Set<Object> getKeys(int depth) {
		if (depth == 1) {
			// At the right depth
			return getKeys();
		} else if (depth > 1) {
			// Dig deeper
			--depth;
			Set<Object> keys = new HashSet<Object>();
			for (Grouped<T> child : getChildren()) {
				keys.addAll(child.getKeys());
			}
			return keys;
		} else {
			// Invalid
			return Collections.emptySet();
		}
	}

	@Override
	public List<T> getValues() {
		List<T> values = new ArrayList<T>();
		for(Grouped<T> child : getChildren()) {
			values.addAll(child.getValues());
		}
		return values;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}

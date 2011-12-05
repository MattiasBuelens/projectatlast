package projectatlast.group;

import java.util.*;

/**
 * A leaf node in a grouped collection.
 */
public abstract class GroupLeaf<T> implements Grouped<T> {

	protected Object key;

	public GroupLeaf(Object key) {
		this.key = key;
	}

	@Override
	public Object getKey() {
		return key;
	}

	/**
	 * Get the key of the immediate children in this collection.
	 * 
	 * <p>
	 * For a leaf node, the results only contain the key of the node itself.
	 */
	@Override
	public Set<Object> getKeys() {
		return Collections.singleton(getKey());
	}

	/**
	 * Get the keys of the children at a given depth in this collection.
	 * 
	 * <p>
	 * For a leaf node, this is the same as calling {@link #getKeys()}, except
	 * when the depth is invalid as specified by {@link Grouped#getKeys()}.
	 */
	@Override
	public Set<Object> getKeys(int depth) {
		if (depth <= 0) {
			// Invalid
			return Collections.emptySet();
		}
		return getKeys();
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

}

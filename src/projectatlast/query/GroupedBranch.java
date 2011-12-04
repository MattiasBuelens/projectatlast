package projectatlast.query;

import java.util.*;

public class GroupedBranch<T> extends GroupBranch<T> {

	protected Map<Object, Grouped<T>> children = new TreeMap<Object, Grouped<T>>();

	public GroupedBranch(Object key) {
		super(key);
	}

	public GroupedBranch(Object key, Collection<Grouped<T>> children) {
		this(key);
		add(children);
	}

	/**
	 * Add a child to this branch.
	 * 
	 * @param child
	 *            The new child.
	 */
	public void add(Grouped<T> child) {
		this.children.put(child.getKey(), child);
	}

	/**
	 * Add children to this branch.
	 * 
	 * @param children
	 *            The children to add.
	 */
	public void add(Collection<Grouped<T>> children) {
		for (Grouped<T> child : children) {
			add(child);
		}
	}

	@Override
	public Set<Object> getKeys() {
		return this.children.keySet();
	}

	@Override
	public List<Grouped<T>> getChildren() {
		return new ArrayList<Grouped<T>>(children.values());
	}

}

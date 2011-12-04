package projectatlast.query;

import java.util.*;

public class GroupableBranch<T> extends GroupBranch<T> implements Groupable<T> {

	protected Map<Object, Groupable<T>> children = new TreeMap<Object, Groupable<T>>();

	public GroupableBranch(Object key) {
		super(key);
	}

	public GroupableBranch(Object key, Collection<Groupable<T>> children) {
		this(key);
		add(children);
	}

	public void add(Groupable<T> child) {
		this.children.put(child.getKey(), child);
	}

	public void add(Collection<Groupable<T>> children) {
		for (Groupable<T> child : children) {
			add(child);
		}
	}

	@Override
	public Set<Object> getKeys() {
		return this.children.keySet();
	}

	@Override
	public List<Groupable<T>> getChildren() {
		return new ArrayList<Groupable<T>>(children.values());
	}

	@Override
	public List<T> getValues() {
		List<T> values = new ArrayList<T>();
		for (Groupable<T> child : children.values()) {
			values.addAll(child.getValues());
		}
		return values;
	}

	@Override
	public Groupable<T> group(Group group) {
		Iterator<Map.Entry<Object, Groupable<T>>> it = children.entrySet()
				.iterator();
		while (it.hasNext()) {
			// Get child
			Map.Entry<Object, Groupable<T>> entry = it.next();
			Groupable<T> child = entry.getValue();
			// Replace child with grouped child
			Groupable<T> newChild = child.group(group);
			entry.setValue(newChild);
		}
		return this;
	}

	@Override
	public <V> Groupable<V> transform(Function<List<T>, List<V>> function) {
		GroupableBranch<V> branch = new GroupableBranch<V>(getKey());
		for (Groupable<T> child : children.values()) {
			branch.add(child.transform(function));
		}
		return branch;
	}

	@Override
	public <V> Grouped<V> parse(Function<List<T>, V> function) {
		GroupedBranch<V> branch = new GroupedBranch<V>(getKey());
		for (Groupable<T> child : children.values()) {
			branch.add(child.parse(function));
		}
		return branch;
	}

}

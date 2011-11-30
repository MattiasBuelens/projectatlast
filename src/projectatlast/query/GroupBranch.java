package projectatlast.query;

import java.util.*;

public class GroupBranch<T> implements Groupable<T> {

	protected Object key;
	protected Map<Object, Groupable<T>> children = new LinkedHashMap<Object, Groupable<T>>();
	
	public GroupBranch(Object key) {
		this.key = key;
	}
	
	public GroupBranch(Object key, Collection<Groupable<T>> children) {
		this(key);
		add(children);
	}
	
	public void add(Groupable<T> child) {
		this.children.put(child.getKey(), child);
	}
	
	public void add(Collection<Groupable<T>> children) {
		for(Groupable<T> child : children) {
			add(child);
		}
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public Set<Object> getKeys() {
		return this.children.keySet();
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
			for (Groupable<T> child : this.children.values()) {
				keys.addAll(child.getKeys());
			}
			return keys;
		} else {
			// Invalid
			return Collections.emptySet();
		}
	}

	@Override
	public List<Groupable<T>> getChildren() {
		return new ArrayList<Groupable<T>>(children.values());
	}

	@Override
	public List<T> getValues() {
		List<T> activities = new ArrayList<T>();
		for(Groupable<T> child : children.values()) {
			activities.addAll(child.getValues());
		}
		return activities;
	}
	
	@Override
	public Groupable<T> group(Group group) {
		Iterator<Map.Entry<Object, Groupable<T>>> it = children.entrySet().iterator();
		while(it.hasNext()) {
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
		GroupBranch<V> branch = new GroupBranch<V>(getKey());
		for(Groupable<T> child : children.values()) {
			branch.add(child.transform(function));
		}
		return branch;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}

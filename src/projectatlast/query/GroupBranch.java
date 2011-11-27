package projectatlast.query;

import java.util.*;

public class GroupBranch<T> implements Groupable<T> {

	protected Object key;
	protected List<Groupable<T>> children = new ArrayList<Groupable<T>>();
	
	public GroupBranch(Object key) {
		this.key = key;
	}
	
	public GroupBranch(Object key, Collection<Groupable<T>> children) {
		this(key);
		add(children);
	}
	
	public void add(Groupable<T> child) {
		children.add(child);
	}
	
	public void add(Collection<Groupable<T>> children) {
		this.children.addAll(children);
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public List<Groupable<T>> getChildren() {
		return children;
	}

	@Override
	public List<T> getValues() {
		List<T> activities = new ArrayList<T>();
		for(Groupable<T> child : children) {
			activities.addAll(child.getValues());
		}
		return activities;
	}
	
	@Override
	public Groupable<T> group(Group group) {
		ListIterator<Groupable<T>> it = children.listIterator();
		while(it.hasNext()) {
			// Replace child with grouped child
			Groupable<T> child = it.next();
			Groupable<T> newChild = child.group(group);
			it.set(newChild);
		}
		return this;
	}

	@Override
	public <V> Groupable<V> transform(Function<List<T>, List<V>> function) {
		GroupBranch<V> branch = new GroupBranch<V>(key);
		for(Groupable<T> child : children) {
			branch.add(child.transform(function));
		}
		return branch;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}

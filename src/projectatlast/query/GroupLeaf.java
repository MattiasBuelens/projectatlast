package projectatlast.query;

import java.util.*;

public class GroupLeaf<T> implements Groupable<T> {

	protected Object key;
	protected List<T> values = new ArrayList<T>();
	
	public GroupLeaf(Object key) {
		this.key = key;
	}
	
	public GroupLeaf(Object key, List<T> values) {
		this.key = key;
		add(values);
	}
	
	public void add(T value) {
		values.add(value);
	}
	
	public void add(Collection<T> values) {
		this.values.addAll(values);
	}

	@Override
	public Object getKey() {
		return key;
	}

	@Override
	public List<Groupable<T>> getChildren() {
		return Collections.emptyList();
	}
	
	@Override
	public List<T> getValues() {
		return values;
	}
	
	@Override
	public Groupable<T> group(Group group) {
		// Group activities
		Map<Object, List<T>> leafs = group.group(values);
		// Construct branch
		GroupBranch<T> branch = new GroupBranch<T>(getKey());
		for(Map.Entry<Object, List<T>> entry : leafs.entrySet()) {
			GroupLeaf<T> leaf = new GroupLeaf<T>(entry.getKey(), entry.getValue());
			branch.add(leaf);
		}
		return branch;
	}

	@Override
	public <V> Groupable<V> transform(Function<List<T>, List<V>> function) {
		GroupLeaf<V> leaf = new GroupLeaf<V>(getKey());
		leaf.add(function.apply(getValues()));
		return leaf;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

}

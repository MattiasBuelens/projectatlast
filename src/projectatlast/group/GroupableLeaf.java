package projectatlast.group;

import projectatlast.query.Function;

import java.util.*;

public class GroupableLeaf<T> extends GroupLeaf<T> implements Groupable<T> {

	protected Object key;
	protected List<T> values = new ArrayList<T>();

	public GroupableLeaf(Object key) {
		super(key);
	}

	public GroupableLeaf(Object key, List<T> values) {
		this(key);
		add(values);
	}

	/**
	 * Add a value to this leaf.
	 * 
	 * @param value
	 *            The new value.
	 */
	public void add(T value) {
		values.add(value);
	}

	/**
	 * Add values to this leaf.
	 * 
	 * @param value
	 *            The values to add.
	 */
	public void add(Collection<T> values) {
		this.values.addAll(values);
	}

	@Override
	public List<T> getValues() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public List<Groupable<T>> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public Groupable<T> group(Group group) {
		// Group activities
		Map<Object, List<T>> leafs = group.group(values);
		// Construct branch from map
		GroupableBranch<T> branch = new GroupableBranch<T>(getKey());
		for (Map.Entry<Object, List<T>> entry : leafs.entrySet()) {
			GroupableLeaf<T> leaf = new GroupableLeaf<T>(entry.getKey(),
					entry.getValue());
			branch.add(leaf);
		}
		return branch;
	}

	@Override
	public <V> Groupable<V> transform(Function<List<T>, List<V>> function) {
		// Clone values first to prevent external changes
		List<T> values = new ArrayList<T>(getValues());
		// Apply transformation
		List<V> newValues = function.apply(values);
		// Create new leaf
		return new GroupableLeaf<V>(getKey(), newValues);
	}

	@Override
	public <V> Grouped<V> parse(Function<List<T>, V> function) {
		// Clone values first to prevent external changes
		List<T> values = new ArrayList<T>(getValues());
		// Parse values
		V parsedValue = function.apply(values);
		// Create new leaf
		return new GroupedLeaf<V>(getKey(), parsedValue);
	}

}

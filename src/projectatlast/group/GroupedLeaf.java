package projectatlast.group;

import java.util.*;

public class GroupedLeaf<T> extends GroupLeaf<T> implements Grouped<T> {

	protected Object key;
	protected T value;

	public GroupedLeaf(Object key) {
		super(key);
	}

	public GroupedLeaf(Object key, T value) {
		this(key);
		setValue(value);
	}

	/**
	 * Get the value of this leaf.
	 * 
	 * @return The value.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Set the value of this leaf.
	 * 
	 * @param value
	 *            The value.
	 */
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public List<T> getValues() {
		return Collections.singletonList(getValue());
	}

	@Override
	public List<Grouped<T>> getChildren() {
		return Collections.emptyList();
	}

}

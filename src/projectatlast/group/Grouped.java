package projectatlast.group;

import java.util.List;
import java.util.Set;

/**
 * A grouped collection.
 * 
 * @param <T>
 *            The type of the grouped object.
 */
public interface Grouped<T> {
	/**
	 * Get the key associated with this collection.
	 * 
	 * @return The key.
	 */
	public Object getKey();

	/**
	 * Get the keys of the immediate children in this collection.
	 * 
	 * @return The keys.
	 */
	public Set<Object> getKeys();

	/**
	 * Get the keys of the children at a given depth in this collection.
	 * 
	 * <p>
	 * The immediate children are at depth 1, their children are at depth 2 and
	 * so on. Nothing is returned when the depth is invalid (i.e. zero or
	 * negative).
	 * 
	 * @param depth
	 *            The depth.
	 * @return The keys.
	 */
	public Set<Object> getKeys(int depth);

	/**
	 * Get the list of objects contained in all children of this collection.
	 * 
	 * @return The objects.
	 */
	public List<T> getValues();

	/**
	 * Get the list of children in this collection.
	 * 
	 * @return The children.
	 */
	public List<? extends Grouped<T>> getChildren();

	/**
	 * Checks whether this collection is a leaf node, i.e. the collection does
	 * not contain other grouped collections.
	 * 
	 * @return True if this collection is a leaf node, false otherwise.
	 */
	public boolean isLeaf();

}

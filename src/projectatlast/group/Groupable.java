package projectatlast.group;

import projectatlast.query.Function;

import java.util.List;

/**
 * A collection which can be grouped.
 * 
 * @param <T>
 *            The type of the groupable object.
 */
public interface Groupable<T> extends Grouped<T> {
	@Override
	public List<? extends Groupable<T>> getChildren();
	
	@Override
	public Groupable<T> getChild(Object key);

	/**
	 * Group this collection using a given {@link Group}.
	 * 
	 * <p>
	 * The caller is responsible for assuring the <code>Group</code> is
	 * compatible with the type of objects stored in this collection.
	 * 
	 * @param group
	 *            The group to use.
	 * @return
	 */
	public Groupable<T> group(Group group);

	/**
	 * Transform this collection into a new groupable collection of a different
	 * type.
	 * 
	 * @param function
	 *            The transformation function.
	 * @return A new groupable collection.
	 */
	public <V> Groupable<V> transform(Function<List<T>, List<V>> function);

	/**
	 * Parses the objects in this collection into a new grouped but no longer
	 * groupable collection.
	 * 
	 * @param function
	 *            The parsing function which consumes all objects and returns a single value.
	 * @return A new grouped collection.
	 */
	public <V> Grouped<V> parse(Function<List<T>, V> function);
}
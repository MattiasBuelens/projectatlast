package projectatlast.query;

import projectatlast.tracking.Activity;

import java.util.List;

import com.googlecode.objectify.Query;

public abstract class Option {

	/**
	 * Get the kind of entity this query option acts upon.
	 * 
	 * @return the entity class.
	 */
	public abstract Class<?> getKind();

	/**
	 * Apply the query option on a query.
	 * 
	 * @param query
	 *            The query.
	 */
	public abstract void apply(Query<?> query);

	/**
	 * Checks whether this option can be applied to a given class.
	 * 
	 * An option can only be applied to a class if that class is a super class
	 * or the same class as the option's kind. In other words, the option's kind
	 * must be assignable from the given glass.
	 * 
	 * @param cls
	 *            The query kind.
	 * @return
	 */
	public boolean appliesTo(Class<?> cls) {
		return getKind().isAssignableFrom(cls);
	}

	/**
	 * Process the resulting list of activities.
	 * 
	 * @param activities
	 *            The list of activities.
	 */
	public <T extends Activity> void process(List<T> activities) {

	}

}

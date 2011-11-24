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
	abstract Class<?> getKind();

	/**
	 * Apply the query option on a query.
	 * 
	 * @param query
	 *            - the query.
	 */
	abstract void apply(Query<?> query);

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
	 * In case the
	 * 
	 * @param activities
	 *            - a list of activities.
	 * @return the new list of activities
	 */
	List<Activity> process(List<Activity> activities) {
		return activities;
	}

}

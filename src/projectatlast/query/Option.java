package projectatlast.query;

import projectatlast.Activity;

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
	 * @param query - the query.
	 */
	abstract void apply(Query<?> query);

	/**
	 * Process the resulting list of activities.
	 * 
	 * In case the 
	 * 
	 * @param activities - a list of activities.
	 * @return the new list of activities
	 */
	List<Activity> process(List<Activity> activities) {
		return activities;
	}

}

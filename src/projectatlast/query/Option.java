package projectatlast.query;

import projectatlast.tracking.Activity;

import java.io.Serializable;
import java.util.List;

import com.googlecode.objectify.Query;

public abstract class Option implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Option() {}

	/**
	 * Get the entity kind this query option acts upon.
	 * 
	 * @return the entity class.
	 */
	public abstract Class<?> getKind();

	/**
	 * Apply the query option on a query.
	 * 
	 * @param kind
	 *            The query kind.
	 * @param query
	 *            The query.
	 */
	public abstract void apply(Class<?> kind, Query<?> query);

	/**
	 * Check whether this option can be applied to a given query kind.
	 * 
	 * <p>
	 * An option can only be applied to a kind if that kind is a super class or
	 * the same class as the option's kind. In other words, the option's kind
	 * must be assignable from the given query kind.
	 * 
	 * @param kind
	 *            The query kind.
	 * @return
	 */
	public boolean appliesTo(Class<?> kind) {
		return getKind().isAssignableFrom(kind);
	}

	/**
	 * Process the resulting list of activities.
	 * 
	 * @param activities
	 *            The list of activities.
	 */
	public <A extends Activity> void process(List<A> activities) {
		return;
	}

}

package projectatlast.query;

import projectatlast.tracking.Activity;

import com.googlecode.objectify.Query;

public class TypeFilter extends Option {
	private static final long serialVersionUID = 1L;

	String type;

	protected TypeFilter() {}

	public TypeFilter(String type) {
		type(type);
	}

	/**
	 * Get the type.
	 * 
	 * @return the type.
	 */
	public String type() {
		return type;
	}

	/**
	 * Set the type.
	 * 
	 * @param type
	 *            The new type.
	 * @return this (for chaining)
	 */
	public TypeFilter type(String type) {
		this.type = type;
		return this;
	}

	@Override
	public Class<?> getKind() {
		return Activity.class;
	}

	@Override
	public void apply(Class<?> kind, Query<?> query) {
		if(type != null && !type.isEmpty()) {
			query.filter("type", type);
		}
	}
}

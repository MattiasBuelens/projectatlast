/**
 * ComparativeOperator
 * 
 * Enumeration of operators used to compare two values.
 * 
 * @version: 1.0
 * @author: Mattias Buelens en Thomas Goosens
 */

package projectatlast.milestone;

import projectatlast.query.ParseField;

public enum ComparativeOperator {

	EQUALS("=", "equals") {
		@Override
		public boolean compare(long left, long right) {
			return left == right;
		}
	},
	LESS_THAN("<", "less than") {
		@Override
		public boolean compare(long left, long right) {
			return left < right;
		}
	},
	GREATER_THAN(">", "greater than") {
		@Override
		public boolean compare(long left, long right) {
			return left > right;
		}
	},
	LESS_THAN_OR_EQUAL("&le;", "less than or equal to") {
		@Override
		public boolean compare(long left, long right) {
			return left <= right;
		}
	},
	GREATER_THAN_OR_EQUAL("&ge;", "greater than or equal to") {
		@Override
		public boolean compare(long left, long right) {
			return left >= right;
		}
	};

	private String symbol;
	private String humanReadable;

	private ComparativeOperator(String symbol, String humanReadable) {
		this.symbol = symbol;
		this.humanReadable = humanReadable;
	}
	
	public abstract boolean compare(long left, long right);
	
	public String humanReadable() {
		return this.humanReadable;
	}
	
	public String symbol() {
		return this.symbol;
	}
	
	/**
	 * Retrieve the identifier of the operator.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name();
	}

	/**
	 * Retrieve the operator with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The operator.
	 */
	public static ComparativeOperator fromId(String id) {
		return ComparativeOperator.valueOf(id.toUpperCase());
	}
	
	public String toString(){
		return this.humanReadable();
	}
}

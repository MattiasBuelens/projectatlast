/**
 * ComparativeOperator
 * 
 * Enumeration of operators used to compare two values.
 * 
 * @author Mattias Buelens
 * @author Thomas Goosens
 */

package projectatlast.milestone;

public enum ComparativeOperator {
	GREATER_THAN(">", "at least") {
		@Override
		public boolean compare(double left, double right) {
			return left > right;
		}
	},
	LESS_THAN("<", "at most") {
		@Override
		public boolean compare(double left, double right) {
			return left < right;
		}
	};

	private String symbol;
	private String humanReadable;

	private ComparativeOperator(String symbol, String humanReadable) {
		this.symbol = symbol;
		this.humanReadable = humanReadable;
	}

	public abstract boolean compare(double left, double right);

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
		try {
			return ComparativeOperator.valueOf(id.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return this.humanReadable();
	}
}

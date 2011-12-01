/**
 * ComparativeOperator
 * 
 * Enumeration of operators used to compare two values.
 * 
 * @version: 1.0
 * @author: Mattias Buelens en Thomas Goosens
 */

package projectatlast.milestone;

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
	
	public int id() {
		return this.ordinal();
	}
	
	public String toString(){
		return this.humanReadable();
	}
}

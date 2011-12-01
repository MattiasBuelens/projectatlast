package projectatlast.query;

import projectatlast.tracking.Activity;

import java.util.List;

public enum Parser {
	SUM("sum") {
		@Override
		public long parse(List<Activity> activities, ParseField field) {
			long sum = 0;
			for (Activity activity : activities) {
				long current = field.getValue(activity);
				sum += current;
			}
			return sum;
		}
	},
	AVG("average") {
		@Override
		public long parse(List<Activity> activities, ParseField field) {
			if (activities.isEmpty())
				return 0;
			long sum = Parser.SUM.parse(activities, field);
			return sum / activities.size();
		}
	},
	MAX("maximum") {
		@Override
		public long parse(List<Activity> activities, ParseField field) {
			if (activities.isEmpty())
				return 0;
			long max = field.getValue(activities.get(0));
			for (int i = 1, len = activities.size(); i < len; ++i) {
				long current = field.getValue(activities.get(i));
				max = Math.max(max, current);
			}
			return max;
		}
	},
	MIN("minimum") {
		@Override
		public long parse(List<Activity> activities, ParseField field) {
			if (activities.isEmpty())
				return 0;
			long min = field.getValue(activities.get(0));
			for (int i = 1, len = activities.size(); i < len; ++i) {
				long current = field.getValue(activities.get(i));
				min = Math.min(min, current);
			}
			return min;
		}
	};

	private String humanReadable;

	private Parser(String humanReadable) {
		this.humanReadable = humanReadable;
	}

	/**
	 * Retrieve the human readable name of the parser.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/**
	 * Retrieve the identifier of the parser.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name().toLowerCase();
	}

	/**
	 * Retrieve the parser with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The parser.
	 */
	public static Parser fromId(String id) {
		return Parser.valueOf(id.toUpperCase());
	}

	public abstract long parse(List<Activity> activities, ParseField field);
}

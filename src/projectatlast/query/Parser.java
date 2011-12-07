package projectatlast.query;

import projectatlast.tracking.Activity;

import java.util.List;

public enum Parser {
	SUM("total", "a total") {
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
	AVG("average", "an average") {
		@Override
		public long parse(List<Activity> activities, ParseField field) {
			if (activities.isEmpty())
				return 0;
			long sum = Parser.SUM.parse(activities, field);
			return sum / activities.size();
		}
	},
	MAX("maximum", "a maximum") {
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
	MIN("minimum", "a minimum") {
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
	private String sentence;

	private Parser(String humanReadable, String sentence) {
		this.humanReadable = humanReadable;
		this.sentence = sentence;
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
	 * Retrieve the sentence part of the parser.
	 * 
	 * @return The sentence part.
	 */
	public String sentence() {
		return sentence;
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
		try {
			return Parser.valueOf(id.toUpperCase());
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Parse a list of activities on a given parse field.
	 * 
	 * @param activities
	 *            The list of activities.
	 * @param field
	 *            The field to parse on.
	 * @return The parsed value.
	 */
	public abstract long parse(List<Activity> activities, ParseField field);

	/**
	 * Get a function to parse activities on a given parse field.
	 * 
	 * @param field
	 *            The field to parse on.
	 * @return A function which can parse activities.
	 */
	public Function<List<Activity>, Long> asFunction(ParseField field) {
		return new ParseFunction(field);
	}

	class ParseFunction implements Function<List<Activity>, Long> {

		private ParseField field;

		public ParseFunction(ParseField field) {
			this.field = field;
		}

		@Override
		public Long apply(List<Activity> activities) {
			return Parser.this.parse(activities, field);
		}

	}
}

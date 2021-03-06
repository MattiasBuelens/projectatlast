package projectatlast.query;

import projectatlast.tracking.*;

import java.util.ArrayList;
import java.util.List;

public enum ParseField {

	DURATION("time spent", "spend {parser} of {goal}", "hours", null) {
		private static final double MILLISECONDS_IN_HOUR = 60 * 60 * 1000;
		
		@Override
		public Double getValue(Activity activity) {
			return ((double) activity.getDuration()) / MILLISECONDS_IN_HOUR;
		}
	},
	MOOD_MODULUS("overall mood", "reach {parser} mood level of {goal}", "%",
			100l) {
		// Maximum value for scalar product
		private final long maxNorm;
		{
			maxNorm = 2 * this.limit() * this.limit();
		}
		
		@Override
		public Double getValue(Activity activity) {
			if (activity instanceof StudyActivity) {
				Mood mood = ((StudyActivity)activity).getMood();
				long interest = mood.getInterest();
				long comprehension = mood.getComprehension();
				double norm = interest * interest + comprehension * comprehension;
				return (double) (this.limit() * Math.sqrt(norm / maxNorm));
			}
			return null;
		}

		@Override
		public Class<?> getKind() {
			return StudyActivity.class;
		}
	},
	MOOD_INTEREST("mood interest", "reach {parser} interest level of {goal}",
			"%", 100l) {
		@Override
		public Double getValue(Activity activity) {
			if (activity instanceof StudyActivity) {
				Mood mood = ((StudyActivity)activity).getMood();
				return (double) mood.getInterest();
			}
			return null;
		}

		@Override
		public Class<?> getKind() {
			return StudyActivity.class;
		}
	},
	MOOD_COMPREHENSION("mood comprehension",
			"reach {parser} comprehension level of {goal}", "%", 100l) {
		@Override
		public Double getValue(Activity activity) {
			if (activity instanceof StudyActivity) {
				Mood mood = ((StudyActivity)activity).getMood();
				return (double) mood.getComprehension();
			}
			return null;
		}

		@Override
		public Class<?> getKind() {
			return StudyActivity.class;
		}
	},
	PAGES("amount of pages", "study {parser} of {goal}", "pages", null) {
		@Override
		public Double getValue(Activity activity) {
			if (activity instanceof StudyActivity) {
				return (double) ((StudyActivity) activity).getPages();
			}
			return null;
		}

		@Override
		public Class<?> getKind() {
			return StudyActivity.class;
		}
	};

	private String humanReadable;
	private String sentence;
	private String unit;
	private Long limit;

	private ParseField(String humanReadable, String sentence, String unit,
			Long limit) {
		this.humanReadable = humanReadable;
		this.sentence = sentence;
		this.unit = unit;
		this.limit = limit;
	}

	public abstract Double getValue(Activity activity);

	public static List<ParseField> values(Class<?> cls) {
		List<ParseField> values = new ArrayList<ParseField>();
		for (ParseField value : ParseField.values()) {
			if (value.appliesTo(cls)) {
				values.add(value);
			}
		}
		return values;
	}

	public Class<?> getKind() {
		return Activity.class;
	}
	
	public String getKindName() {
		if(StudyActivity.class.isAssignableFrom(getKind()))
			return "study";
		if(FreeTimeActivity.class.isAssignableFrom(getKind()))
			return "freetime";
		return "";
	}

	public boolean appliesTo(Class<?> cls) {
		return getKind().isAssignableFrom(cls);
	}

	/**
	 * Retrieve the human readable name of the parse field.
	 * 
	 * @return The human readable name.
	 */
	public String humanReadable() {
		return humanReadable;
	}

	/**
	 * Retrieve the sentence part of the parse field.
	 * 
	 * @return The sentence part.
	 */
	public String sentence() {
		return sentence;
	}

	/**
	 * Build the sentence part of the parse field.
	 * 
	 * @param parser
	 *            The query parser.
	 * @param goal
	 *            The goal.
	 * @return The sentence part.
	 */
	public String buildSentence(Parser parser, String goal) {
		return sentence().replaceAll("\\{parser\\}", parser.sentence())
				.replaceAll("\\{goal\\}", goal);
	}

	/**
	 * Retrieve the unit of the parse field.
	 * 
	 * @return The name of the unit.
	 */
	public String unit() {
		return unit;
	}

	/**
	 * Retrieve the unit of the parse field.
	 * 
	 * @return The name of the unit.
	 */
	public long limit() {
		return (limit == null) ? -1 : limit;
	}

	/**
	 * Retrieve the identifier of the parse field.
	 * 
	 * @return The identifier.
	 */
	public String id() {
		return this.name().toLowerCase();
	}

	/**
	 * Retrieve the parse field with the given identifier.
	 * 
	 * @param id
	 *            The identifier.
	 * @return The parse field.
	 */
	public static ParseField fromId(String id) {
		try {
			return ParseField.valueOf(id.toUpperCase());
		} catch (Exception e) {
			return null;
		}
	}
}

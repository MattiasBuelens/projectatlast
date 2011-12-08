package projectatlast.query;

import projectatlast.tracking.Activity;
import projectatlast.tracking.StudyActivity;
import projectatlast.tracking.Mood;

import java.util.ArrayList;
import java.util.List;

public enum ParseField {

	DURATION("time spent", "spend {parser} of {goal}", "milliseconds") {
		@Override
		public Long getValue(Activity activity) {
			return activity.getDuration();
		}
	},
	MOOD_MODULUS("overall mood", "reach {parser} mood level of {goal}",
			"%") {
		@Override
		public Long getValue(Activity activity) {
			Mood mood = activity.getMood();
			long interest = mood.getInterest();
			long comprehension = mood.getComprehension();
			long maxValue = 100;
			long maxNorm = 2 * maxValue * maxValue;
			return (long) (maxValue * Math.sqrt((interest * interest + comprehension * comprehension) / maxNorm));
		}
	},
	MOOD_INTEREST("mood interest", "reach {parser} interest level of {goal}",
			"%") {
		@Override
		public Long getValue(Activity activity) {
			return activity.getMood().getInterest();
		}
	},
	MOOD_COMPREHENSION("mood comprehension",
			"reach {parser} comprehension level of {goal}", "%") {
		@Override
		public Long getValue(Activity activity) {
			return activity.getMood().getComprehension();
		}
	},
	PAGES("amount of pages", "study {parser} of {goal}", "pages") {
		@Override
		public Long getValue(Activity activity) {
			if(activity instanceof StudyActivity) {
				return ((StudyActivity)activity).getPages();
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

	private ParseField(String humanReadable, String sentence, String unit) {
		this.humanReadable = humanReadable;
		this.sentence = sentence;
		this.unit = unit;
	}

	public abstract Long getValue(Activity activity);
	
	public static List<ParseField> values(Class<?> cls) {
		List<ParseField> values = new ArrayList<ParseField>();
		for(ParseField value : ParseField.values()) {
			if(value.appliesTo(cls)) {
				values.add(value);
			}
		}
		return values;
	}
	
	public Class<?> getKind() {
		return Activity.class;
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

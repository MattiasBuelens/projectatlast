package projectatlast.milestone;

public class SentenceBuilder extends projectatlast.query.SentenceBuilder {

	protected Milestone milestone;

	public SentenceBuilder(Milestone milestone) {
		super(milestone.getQuery());
		this.milestone = milestone;
	}

	@Override
	protected void intro() {
		builder.append("I want to ");
		goal();
	}

	protected void goal() {
		StringBuilder goalBuilder = new StringBuilder();
		goalBuilder.append(milestone.getOperator().humanReadable()).append(" ");
		goalBuilder.append(milestone.getGoal()).append(" ");
		goalBuilder.append(milestone.getParseField().unit());

		String goal = milestone.getParseField()
				.buildSentence(milestone.getParser(),
						goalBuilder.toString());
		builder.append(goal);
	}

}

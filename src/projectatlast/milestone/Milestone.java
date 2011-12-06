package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.List;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

@Entity
public class Milestone {

	@Id Long id;
	Key<Student> student;
	long goal;
	long startValue;
	Date deadline;
	boolean isCompleted;
	String sentence;
	
	@Unindexed ComparativeOperator operator;
	@Serialized Query query;
	@Unindexed Parser queryParser;
	// MilestoneStatus status;
	@Unindexed ParseField parseField;

	protected Milestone() {}

	public Milestone(Student student, long goal, long startValue,
			Date deadline, String sentence, ComparativeOperator operator, Query query,
			Parser queryParser, ParseField parseField) {
		setGoal(goal);
		setStartValue(startValue);
		setDeadline(deadline);
		setSentence(sentence);
		setOperator(operator);
		setQuery(query);
		setQueryParser(queryParser);
		setParseField(parseField);
		setStudent(Registry.dao().key(student));
	}

	public long getId() {
		return id;
	}

	public Student getStudent() {
		return Registry.studentFinder().getStudent(this.student);
	}

	public void setStudent(Key<Student> student) {
		this.student = student;
	}

	public long getGoal() {
		return goal;
	}

	public void setGoal(long goal) {
		this.goal = goal;
	}

	public long getStartValue() {
		return startValue;
	}

	private void setStartValue(long startValue) {
		this.startValue = startValue;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public void setSentence(String sentence){
		this.sentence = sentence;
	}
	
	public String getSentence(){
		return this.sentence;
	}

	public ComparativeOperator getOperator() {
		return operator;
	}

	public void setOperator(ComparativeOperator operator) {
		this.operator = operator;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Parser getQueryParser() {
		return queryParser;
	}

	public void setQueryParser(Parser queryParser) {
		this.queryParser = queryParser;
	}

	public boolean isCompleted() {
		calculateCompletion();
		return isCompleted;
	}
	
	public boolean isExpired() {
		Date now = new Date();
		return now.after(deadline);
	}

	/**
	 * Calculate whether milestone is completed
	 */
	private boolean calculateCompletion() {
		
		// get activities from query
		List<Activity> activities = query.get().getValues();
		long currentValue = queryParser.parse(activities, getParseField());

		// this test will verify whether the goal set by the user is achieved
		// The 'goal' is being compared using the ComparativeOperator.compare()
		// with the current value
		// of the parser
		boolean test = operator.compare(currentValue, getGoal());

		// edit the completed field

		/** is this necessary? **/
		setCompleted(test);
		return test;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public ParseField getParseField() {
		return parseField;
	}

	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}
}

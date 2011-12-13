package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

@Entity
@Cached
@Unindexed
public class Milestone {

	@Id Long id;
	@Indexed Key<Student> student;
	@Indexed Date deadline;

	boolean isCompleted = false;
	double progress;
	double goal;
	double startValue;
	String sentence;

	ComparativeOperator operator;
	@Serialized Query query;
	Parser queryParser;
	ParseField parseField;

	protected Milestone() {}

	public Milestone(Student student, double goal, double startValue,
			Date deadline, ComparativeOperator operator, Query query,
			Parser queryParser, ParseField parseField) {
		setGoal(goal);
		setStartValue(startValue);
		setDeadline(deadline);
		setSentence("");
		setOperator(operator);
		setQuery(query);
		setParser(queryParser);
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

	public double getGoal() {
		return goal;
	}

	public void setGoal(double goal) {
		this.goal = goal;
	}

	public double getStartValue() {
		return startValue;
	}

	private void setStartValue(double startValue) {
		this.startValue = startValue;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getSentence() {
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

	public Parser getParser() {
		return queryParser;
	}

	public void setParser(Parser queryParser) {
		this.queryParser = queryParser;
	}

	public ParseField getParseField() {
		return parseField;
	}

	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;

		boolean isCompleted = getOperator().compare(progress, getGoal());
		setCompleted(isCompleted);
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public boolean isExpired() {
		Date now = new Date();
		return now.after(deadline);
	}
}

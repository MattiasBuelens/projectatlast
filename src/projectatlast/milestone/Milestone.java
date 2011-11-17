package projectatlast.milestone;

import projectatlast.data.Registry;
import projectatlast.query.*;
import projectatlast.student.Student;
import projectatlast.tracking.Activity;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class Milestone {

	@Id Long id;
	long goal;
	Date deadline;
	ComparativeOperator operator;
	Query query;
	Parser queryParser;
	boolean completed;
	int progress;
	//MilestoneStatus status;
	ParseField parseField;
	
	Key<Student> student;
	
	
	protected Milestone(){}


	public Milestone(Student student,long goal, Date deadline, ComparativeOperator operator,
			Query query, Parser queryParser,ParseField parseField) {
		super();
		setGoal(goal);	
		setDeadline(deadline);
		setOperator(operator);
		setQuery(query);
		setQueryParser(queryParser);
		setParseField(parseField);
		setStudent(Registry.dao().key(student));
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


	public Date getDeadline() {
		return deadline;
	}


	public void setDeadline(Date deadline) {
		this.deadline = deadline;
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
		return completed;
	}


	/**
	 * Calculate whether milestone is completed
	 */
	private boolean calculateCompletion() {

		//get activities from query
		ArrayList<Activity> activities = new ArrayList<Activity>();
		
		//this test will verify whether the goal set by the user is achieved
		//The 'goal' is being compared using the ComparativeOperator.compare() with the current value
		//of the parser
		boolean test = operator.compare(queryParser.parse(activities, getParseField()), getGoal());
		
		//edit the completed field 
		
		/**  is this necessary? **/
		setCompleted(test);
		return test;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		this.progress = progress;
	}


	public ParseField getParseField() {
		return parseField;
	}


	public void setParseField(ParseField parseField) {
		this.parseField = parseField;
	}
	
	//Depricated: no precent will be used. A visual representation of "progress" will be 
	//used instead
	public void calculateProgress(){
		//Has to be filled in.
		
	}
}

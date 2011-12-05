package projectatlast.test;

import projectatlast.group.GroupField;
import projectatlast.query.*;
import projectatlast.student.Student;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class TestClass {
	@Id Long id;
	@Embedded Query query;
	
	Key<Student> student;	
	GroupField sortField;
	ParseField parseField;
	Parser parser;
	String title;
	
	protected TestClass(){}
	
	public TestClass(String i){
		query = new Query();
	}
	
	
}

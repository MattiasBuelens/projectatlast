package projectatlast.data;

import projectatlast.student.Student;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

public class StudentFinder extends Finder {

	public StudentFinder(DAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub

	}
	
	
	public Student getStudent(User user){
		
		return null;
	}
	
	public Student getStudent(Key<Student> key){
		
		return dao.ofy().get(key);
	}
	
	

}

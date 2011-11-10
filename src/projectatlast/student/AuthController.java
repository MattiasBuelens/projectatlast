package projectatlast.student;

import projectatlast.data.Registry;

import org.w3c.dom.UserDataHandler;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthController {

	public static boolean authenticate() {
		return false;
	}

	public static Student getCurrentStudent() {
		return Registry.studentFinder().getStudent(getCurrentUser());
	}

	public static boolean logout() {
		UserServiceFactory.getUserService().createLogoutURL("home.jsp");
		return true;
	}

	/**
	 * Register the current User if not existing already
	 * 
	 * @return
	 */
	public static boolean register() {
		boolean result = true;
		// Check if user exists
		if (getCurrentStudent() == null) {
			// user doesn't exist in the system yet
			Student student = new Student(getCurrentUser());

			// register
			Registry.studentFinder().put(student);
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Returns current logged in user If no user: null is returned
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}

	public static boolean activeSession() {
		
		return UserServiceFactory.getUserService().isUserLoggedIn();
	}
	
	public static boolean isRegistered(User user){
		return Registry.studentFinder().userExists(user);
	}
	
}

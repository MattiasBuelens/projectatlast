package projectatlast.student;

import projectatlast.data.Registry;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthController {

	public static String createLoginURL() {
		return UserServiceFactory.getUserService()
				.createLoginURL("/student/postlogin");
	}

	public static String createLogoutURL() {
		return UserServiceFactory.getUserService()
				.createLogoutURL("/student/login");
	}

	/**
	 * Register the current User if not existing already
	 * 
	 * @return
	 */
	public static boolean register(User user) {
		boolean result = false;
		// If user is valid and not registered
		if (user != null && !isRegistered(user)) {
			// Register new student
			Student student = new Student(user);
			Registry.studentFinder().put(student);
			result = true;
		}

		return result;
	}

	/**
	 * Register the currently logged in user as a student, if not existing
	 * already registered.
	 * 
	 * @return true if the user was registered, false if the user
	 */
	public static boolean register() {
		return register(getCurrentUser());
	}

	/**
	 * Returns the currently logged in user.
	 * 
	 * @return the current user, or null if no user is logged in.
	 * @see {@link #isLoggedIn()}
	 */
	public static User getCurrentUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}

	/**
	 * Returns the currently logged in student.
	 * 
	 * This function will fail if there is no user logged in or if the logged in
	 * user is not registered.
	 * 
	 * @return the currently logged in student, or null.
	 * @see {@link #getCurrentUser()}
	 */
	public static Student getCurrentStudent() {
		return Registry.studentFinder().getStudent(getCurrentUser());
	}

	/**
	 * Checks whether there is a logged in user.
	 * 
	 * @return true if the user is logged in, false otherwise.
	 * @see {@link #getCurrentUser()}
	 */
	public static boolean isLoggedIn() {
		return UserServiceFactory.getUserService().isUserLoggedIn();
	}

	/**
	 * Checks whether the currently logged in user is registered.
	 * 
	 * @param user
	 * @return true if the user is registered, false otherwise.
	 * @see {@link #isLoggedIn()}
	 * @see {@link #isRegistered(User)}
	 */
	public static boolean isRegistered() {
		return isRegistered(getCurrentUser());
	}

	/**
	 * Checks whether the given user is registered, that is whether there is a
	 * student associated with this user.
	 * 
	 * @param user
	 * @return true if the user is registered, false otherwise.
	 */
	public static boolean isRegistered(User user) {
		if (user == null)
			return false;
		return Registry.studentFinder().userExists(user);
	}

}

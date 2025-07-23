package aq.project.exceptions;

public class UserIdNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserIdNotFoundException(String msg) {
		super(msg);
	}
}

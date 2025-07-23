package aq.project.exceptions;

public class UserLoginNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserLoginNotFoundException(String msg) {
		super(msg);
	}
}

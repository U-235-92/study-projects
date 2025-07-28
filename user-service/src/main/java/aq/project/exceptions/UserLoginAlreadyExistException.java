package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserLoginAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	private String login;
	
	@Override
	public String getMessage() {
		return String.format("User with login [ %s ] already exists", login);
	}
}

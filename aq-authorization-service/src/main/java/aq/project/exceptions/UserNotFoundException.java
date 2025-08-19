package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String login;

	@Override
	public String getMessage() {
		return String.format("User with login %s wasn't found", login);
	}
}

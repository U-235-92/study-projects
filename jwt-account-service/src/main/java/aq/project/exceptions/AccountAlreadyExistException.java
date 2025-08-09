package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String login;
	
	@Override
	public String getMessage() {
		return String.format("Account with login [ %s ] already exists", login);
	}
}

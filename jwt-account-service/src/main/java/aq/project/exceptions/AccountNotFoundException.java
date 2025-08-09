package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String login;
	
	@Override
	public String getMessage() {
		return String.format("Account with login [ %s ] wasn't found", login);
	}
}

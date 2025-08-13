package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockedAccountException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String login;
	
	@Override
	public String getMessage() {
		return String.format("Account with login [ %s ] is blocked", login);
	}
}

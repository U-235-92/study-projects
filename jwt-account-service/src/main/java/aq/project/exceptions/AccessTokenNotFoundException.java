package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessTokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String login;
	
	@Override
	public String getMessage() {
		return String.format("Access token for account with login [ %s ] wasn't found", login);
	}
}

package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccessDeniedException extends Exception {

	private static final long serialVersionUID = 1L;

	private String login;
	private String operation;

	@Override
	public String getMessage() {
		return String.format("Access denied in operation %s to %s ", operation, login);
	}
}

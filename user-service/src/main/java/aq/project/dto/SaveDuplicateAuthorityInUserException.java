package aq.project.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaveDuplicateAuthorityInUserException extends Exception {

	private static final long serialVersionUID = 1L;

	private String duplicateAuthorityName;
	private String login;
	
	@Override
	public String getMessage() {
		return String.format("Attempt to save duplicate authority [ %s ] in user with login [ %s ]", duplicateAuthorityName, login);
	}
}

package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String email;
	
	@Override
	public String getMessage() {
		return String.format("Email [ %s ] is already in use", email);
	}
}

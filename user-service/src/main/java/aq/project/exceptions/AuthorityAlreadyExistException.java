package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorityAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	private String authority;
	
	@Override
	public String getMessage() {
		return String.format("Authority with name [ %s ] already exist", authority);
	}
}

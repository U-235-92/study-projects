package aq.project.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnknownAuthorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String author;
	
	@Override
	public String getMessage() {
		return String.format("Author with name %s is unknown", author);
	}
}

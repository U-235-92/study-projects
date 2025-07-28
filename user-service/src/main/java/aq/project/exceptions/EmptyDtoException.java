package aq.project.exceptions;

public class EmptyDtoException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "DTO is null";
	}
}

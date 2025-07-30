package aq.project.exceptions;

public class NullDtoException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "DTO is null";
	}
}

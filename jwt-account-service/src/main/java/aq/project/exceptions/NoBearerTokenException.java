package aq.project.exceptions;

public class NoBearerTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Authorization header must contain Bearer token";
	}
}

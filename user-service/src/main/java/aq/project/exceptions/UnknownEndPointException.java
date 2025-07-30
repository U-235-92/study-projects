package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnknownEndPointException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String endPoint;
	
	@Override
	public String getMessage() {
		return String.format("Unknown end-point [ %s ]", endPoint);
	}
}

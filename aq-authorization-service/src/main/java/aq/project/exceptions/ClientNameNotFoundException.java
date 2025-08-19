package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientNameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String name;

	@Override
	public String getMessage() {
		return String.format("Client with name %s wasn't found", name);
	}
}

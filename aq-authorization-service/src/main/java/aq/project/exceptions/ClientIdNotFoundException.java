package aq.project.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String id;

	@Override
	public String getMessage() {
		return String.format("Client with id %s wasn't found", id);
	}
}

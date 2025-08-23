package aq.project.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	@Override
	public String getMessage() {
		return String.format("Message with id %d wasn't found", id);
	}
}

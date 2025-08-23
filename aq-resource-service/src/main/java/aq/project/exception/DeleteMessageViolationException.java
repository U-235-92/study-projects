package aq.project.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteMessageViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String editor;
	private String editable;
	
	@Override
	public String getMessage() {
		return String.format("%s isn't allow to delete messages of %s", editor, editable);
	}
}

package aq.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnknownUserAuthorityException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;
}

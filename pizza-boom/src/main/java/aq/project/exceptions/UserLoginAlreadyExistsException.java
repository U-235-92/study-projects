package aq.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
}

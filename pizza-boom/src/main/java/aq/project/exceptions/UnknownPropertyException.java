package aq.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnknownPropertyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String message;
}

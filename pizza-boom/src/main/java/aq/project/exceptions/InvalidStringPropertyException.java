package aq.project.exceptions;

import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidStringPropertyException extends Exception {

	private static final long serialVersionUID = 1L;

	private ConstraintViolation<String> violation;
//	private String propertyName;
	
	@Override
	public String getMessage() {
		String violationName = violation.getPropertyPath().toString();
		String violationValue = violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString();
		return String.format("Invalid property %s: %s", violationName, violationValue);
	}
}

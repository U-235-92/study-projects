package aq.project.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import aq.project.dto.ExtendedUserResponse;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class InvalidExtendedUserResponseException extends Exception {

	private static final long serialVersionUID = 1L;

	@NonNull
	private Set<ConstraintViolation<ExtendedUserResponse>> violations;
	
	@Override
	public String getMessage() {
		String invalidUserData = violations.stream()
					.map(violation -> handleViolation(violation))
					.collect(Collectors.joining(",\n"));
		return new StringBuilder("Invalid response data:\n").append(invalidUserData).toString();
	}
	
	private String handleViolation(ConstraintViolation<ExtendedUserResponse> violation) {
		String propertyName = violation.getPropertyPath().toString();
		String propertyValue = (violation.getInvalidValue() == null) ? null : violation.getInvalidValue().toString();
		return String.format("%s: %s", propertyName, propertyValue);
	}
}

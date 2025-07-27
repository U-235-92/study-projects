package aq.project.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import aq.project.dto.BasicUserResponse;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class InvalidBasicUserResponseException extends Exception {

	private static final long serialVersionUID = 1L;

	@NonNull
	private Set<ConstraintViolation<BasicUserResponse>> violations;
	
	@Override
	public String getMessage() {
		String invalidUserData = violations.stream()
					.map(violation -> handleViolation(violation))
					.collect(Collectors.joining(",\n"));
		return new StringBuilder("Invalid response data:\n").append(invalidUserData).toString();
	}
	
	private String handleViolation(ConstraintViolation<BasicUserResponse> violation) {
		String propertyName = violation.getPropertyPath().toString();
		String propertyValue = (violation.getInvalidValue() == null) ? null : violation.getInvalidValue().toString();
		return String.format("%s: %s", propertyName, propertyValue);
	}
}

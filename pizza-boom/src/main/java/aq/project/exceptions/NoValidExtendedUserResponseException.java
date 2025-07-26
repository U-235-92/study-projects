package aq.project.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import aq.project.dto.ExtendedUserResponse;
import jakarta.validation.ConstraintViolation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoValidExtendedUserResponseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	private Set<ConstraintViolation<ExtendedUserResponse>> violations;
	
	@Override
	public String getMessage() {
		String invalidUserData = violations.stream()
					.map(violation -> String.format("%s: %s", violation.getPropertyPath().toString(), violation.getInvalidValue().toString()))
					.collect(Collectors.joining(", "));
		return new StringBuilder("Invalid user data -> ").append(invalidUserData).toString();
	}
}

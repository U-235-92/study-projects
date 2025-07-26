package aq.project.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import aq.project.dto.ExtendedUserRequest;
import jakarta.validation.ConstraintViolation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoValidExtendedUserRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	private Set<ConstraintViolation<ExtendedUserRequest>> violations;
	
	@Override
	public String getMessage() {
		String invalidUserData = violations.stream()
					.map(violation -> String.format("%s: %s", violation.getPropertyPath().toString(), violation.getInvalidValue().toString()))
					.collect(Collectors.joining(", "));
		return new StringBuilder("Invalid user data -> ").append(invalidUserData).toString();
	}
}

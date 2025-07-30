package aq.project.aspects;

import java.util.Set;

import aq.project.dto.PasswordRequest;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class PasswordChecker {

	protected static void checkPasswordRequest(PasswordRequest passwordRequest, Validator validator) throws InvalidPropertyException {
		Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(passwordRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong password request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}
}

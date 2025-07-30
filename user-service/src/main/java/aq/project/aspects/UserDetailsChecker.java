package aq.project.aspects;

import java.util.Set;

import aq.project.dto.UserDetailsRequest;
import aq.project.exceptions.EmailAlreadyExistException;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import aq.project.repositories.UserDetailsRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class UserDetailsChecker {

	protected static void checkEmailAlreadyInUse(UserDetailsRepository userDetailsRepository, String email) throws EmailAlreadyExistException {
		if(userDetailsRepository.findByEmail(email).isPresent())
			throw new EmailAlreadyExistException(email);
	}
	
	protected static void chechUserDetailsRequestViolations(UserDetailsRequest userDetailsRequest, Validator validator) throws InvalidPropertyException {
		Set<ConstraintViolation<UserDetailsRequest>> violations = validator.validate(userDetailsRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong user details request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}
}

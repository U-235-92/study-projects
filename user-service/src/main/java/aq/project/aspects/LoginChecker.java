package aq.project.aspects;

import java.util.Set;

import aq.project.dto.LoginUpdateRequest;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.exceptions.LoginAlreadyExistException;
import aq.project.exceptions.LoginNotFoundException;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import aq.project.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class LoginChecker {

	protected static void checkLoginAlreadyExist(UserRepository userRepository, String login) throws LoginAlreadyExistException {
		if(userRepository.findByLogin(login).isPresent())
			throw new LoginAlreadyExistException(login);
	}
	
	protected static void checkUserLoginNotFound(UserRepository userRepository, String login) throws LoginNotFoundException {
		if(userRepository.findByLogin(login).isEmpty())
			throw new LoginNotFoundException(login);
	}
	
	protected static void checkLoginUpdateRequest(LoginUpdateRequest loginRequest, Validator validator) throws InvalidPropertyException {
		Set<ConstraintViolation<LoginUpdateRequest>> violations = validator.validate(loginRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong login update request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}
}

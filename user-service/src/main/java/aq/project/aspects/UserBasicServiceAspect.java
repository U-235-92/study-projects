package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import aq.project.dto.BasicUserCreationRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.exceptions.AccessDeniedException;
import aq.project.exceptions.EmailAlreadyExistException;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.exceptions.LoginAlreadyExistException;
import aq.project.exceptions.LoginNotFoundException;
import aq.project.exceptions.NullDtoException;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import aq.project.repositories.UserDetailsRepository;
import aq.project.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class UserBasicServiceAspect {

	private final Validator validator;
	private final UserRepository userRepository;
	private final UserDetailsRepository userDetailsRepository;
	
	@Before("execution(void aq.project.services.UserBasicService.createUser(..)) && args(basicUserRequest)")
	public void checkCreateUser(BasicUserCreationRequest basicUserRequest) throws NullDtoException, InvalidPropertyException, LoginAlreadyExistException, EmailAlreadyExistException {
		checkNullUserRequest(basicUserRequest);
		checkUserRequestViolations(basicUserRequest);
		LoginChecker.checkLoginAlreadyExist(userRepository, basicUserRequest.getLogin());
		UserDetailsChecker.checkEmailAlreadyInUse(userDetailsRepository, basicUserRequest.getEmail());
	}
	
	@Around("execution(* aq.project.services.UserBasicService.readUser(..)) && args(login, authentication)")
	public BasicUserResponse checkReadUser(ProceedingJoinPoint pjp, String login, Authentication authentication) throws InvalidPropertyException, AccessDeniedException, Throwable {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		checkSameLoginUserOperationAccess(login, authentication, "read basic user data");
		BasicUserResponse basicUserResponse = (BasicUserResponse) pjp.proceed(pjp.getArgs());
		checUserResponseViolations(basicUserResponse);
		return basicUserResponse;
	}
	
	@Before("execution(void aq.project.services.UserBasicService.deleteUser(..)) && args(login, authentication)")
	public void checkUserDelete(String login, Authentication authentication) throws LoginNotFoundException, AccessDeniedException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		checkSameLoginUserOperationAccess(login, authentication, "delete user");
	}
	
	@Before("execution(void aq.project.services.UserBasicService.updateUserPassword(..)) && args(login, passwordRequest, authentication)")
	public void checkUpdateUserPassword(String login, PasswordRequest passwordRequest, Authentication authentication) throws LoginNotFoundException, AccessDeniedException, InvalidPropertyException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		checkSameLoginUserOperationAccess(login, authentication, "update password");
		PasswordChecker.checkPasswordRequest(passwordRequest, validator);
	}
	
	@Before("execution(void aq.project.services.UserBasicService.updateUserDetails(..)) && args(login, userDetailsRequest, authentication)")
	public void checkUpdateUserDetails(String login, UserDetailsRequest userDetailsRequest, Authentication authentication) throws LoginNotFoundException, AccessDeniedException, InvalidPropertyException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		checkSameLoginUserOperationAccess(login, authentication, "update user details");
		UserDetailsChecker.chechUserDetailsRequestViolations(userDetailsRequest, validator);
	}
	
	private void checkNullUserRequest(BasicUserCreationRequest basicUserRequest) throws NullDtoException {
		if(basicUserRequest == null)
			throw new NullDtoException();
	}
	
	private void checkUserRequestViolations(BasicUserCreationRequest basicUserRequest) throws InvalidPropertyException {
		Set<ConstraintViolation<BasicUserCreationRequest>> violations = validator.validate(basicUserRequest);
		if(!violations.isEmpty()) {
			throw new InvalidPropertyException("Wrong user creation request parameter(s)", 
					violations.stream().map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
		}
	}
	
	private void checUserResponseViolations(BasicUserResponse basicUserResponse) throws InvalidPropertyException {
		Set<ConstraintViolation<BasicUserResponse>> violations = validator.validate(basicUserResponse);
		if(!violations.isEmpty()) {
			throw new InvalidPropertyException("Wrong basic user response parameter(s)", 
					violations.stream().map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
		}
	}
	
	private void checkSameLoginUserOperationAccess(String login, Authentication authentication, String operation) throws AccessDeniedException {
		if(!login.equals(authentication.getName()))
			throw new AccessDeniedException(authentication.getName(), operation);
	}
}

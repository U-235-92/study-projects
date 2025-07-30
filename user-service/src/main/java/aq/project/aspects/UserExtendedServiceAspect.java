package aq.project.aspects;

import java.util.List;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AuthorityRequest;
import aq.project.dto.ExtendedUserCreationRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.dto.LoginUpdateRequest;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserAuthoritiesUpdateRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.entities.Authority;
import aq.project.exceptions.AuthorityAlreadyExistException;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.exceptions.EmailAlreadyExistException;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.exceptions.LoginAlreadyExistException;
import aq.project.exceptions.LoginNotFoundException;
import aq.project.exceptions.NullDtoException;
import aq.project.mappers.UserAuthorityRequestToAuthorityMapper;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import aq.project.repositories.UserDetailsRepository;
import aq.project.repositories.UserRepository;
import aq.project.utils.AuthorityHolder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class UserExtendedServiceAspect {

	private final Validator validator;
	private final UserRepository userRepository;
	private final AuthorityHolder authorityHolder;
	private final UserDetailsRepository userDetailsRepository;
	private final UserAuthorityRequestToAuthorityMapper userAuthorityRequestMapper;
	
	@Before("execution(void aq.project.services.UserExtendedService.createUser(..)) && args(extendedUserCreationRequest)")
	private void checkExtendedCreateUser(ExtendedUserCreationRequest extendedUserCreationRequest) throws NullDtoException, InvalidPropertyException, LoginAlreadyExistException, EmailAlreadyExistException, AuthorityNotFoundException {
		if(extendedUserCreationRequest == null)
			throw new NullDtoException();
		checkExtendedUserRequestViolations(extendedUserCreationRequest);
		LoginChecker.checkLoginAlreadyExist(userRepository, extendedUserCreationRequest.getLogin());
		UserDetailsChecker.checkEmailAlreadyInUse(userDetailsRepository, extendedUserCreationRequest.getEmail());
		checkValidUserAuthorities(extendedUserCreationRequest);
	}
	
	private void checkExtendedUserRequestViolations(ExtendedUserCreationRequest extendedUserCreationRequest) throws InvalidPropertyException {
		Set<ConstraintViolation<ExtendedUserCreationRequest>> violations = validator.validate(extendedUserCreationRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong extended user creation request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}
	
	private void checkValidUserAuthorities(ExtendedUserCreationRequest extendedUserCreationRequest) throws AuthorityNotFoundException {
		for(String authority : extendedUserCreationRequest.getAuthorities()) {
			authorityHolder.isAuthorityNotFound(authority);
		}
	}
	
	@Before("execution(void aq.project.services.UserService.deleteUser(..)) && args(login)")
	private void checkExtendedDeleteUser(String login) throws LoginNotFoundException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
	}
	
	@Around("execution(* aq.project.services.UserService.readUser(..)) && args(login)")
	private ExtendedUserResponse checkExtededUserRead(ProceedingJoinPoint pjp, String login) throws LoginNotFoundException, InvalidPropertyException, Throwable {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		ExtendedUserResponse extendedUserResponse = (ExtendedUserResponse) pjp.proceed(pjp.getArgs());
		checkExtendedUserResponseViolations(extendedUserResponse);
		return extendedUserResponse;
	}
	
	@Around("execution(* aq.project.services.UserService.readAllUsers())")
	private List<ExtendedUserResponse> checkExtededAllUserRead(ProceedingJoinPoint pjp) throws InvalidPropertyException, Throwable {
		@SuppressWarnings("unchecked")
		List<ExtendedUserResponse> extendedUserResponseList = (List<ExtendedUserResponse>) pjp.proceed(pjp.getArgs());
		checkExtendedUserResponseViolations(extendedUserResponseList);
		return extendedUserResponseList;
	}
	
	private void checkExtendedUserResponseViolations(List<ExtendedUserResponse> extendedUserResponseList) throws InvalidPropertyException {
		for(ExtendedUserResponse response : extendedUserResponseList) {
			checkExtendedUserResponseViolations(response);
		}
	}
	
	private void checkExtendedUserResponseViolations(ExtendedUserResponse extendedserResponse) throws InvalidPropertyException {
		Set<ConstraintViolation<ExtendedUserResponse>> violations = validator.validate(extendedserResponse);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong extended user response parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}

	@Before("execution(void aq.project.services.UserExtendedService.updateUserPassword(..)) && args(login, passwordRequest)")
	private void checkUpdateUserPassword(String login, PasswordRequest passwordRequest) throws LoginNotFoundException, InvalidPropertyException, NullDtoException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		if(passwordRequest == null)
			throw new NullDtoException();
		PasswordChecker.checkPasswordRequest(passwordRequest, validator);
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.updateUserLogin(..)) && args(loginUpdateRequest)")
	private void checkUpdateUserLogin(LoginUpdateRequest loginUpdateRequest) throws LoginNotFoundException, InvalidPropertyException, LoginAlreadyExistException, NullDtoException {
		if(loginUpdateRequest == null)
			throw new NullDtoException();
		LoginChecker.checkLoginUpdateRequest(loginUpdateRequest, validator);
		LoginChecker.checkUserLoginNotFound(userRepository, loginUpdateRequest.getOldLogin());
		LoginChecker.checkLoginAlreadyExist(userRepository, loginUpdateRequest.getNewLogin());
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.blockUser(..)) && args(login)")
	private void checkBlockUser(String login) throws LoginNotFoundException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.unblockUser(..)) && args(login)")
	private void checkUnblockUser(String login) throws LoginNotFoundException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.updateUserDetails(..)) && args(login, userDetailsRequest)")
	public void checkUpdateUserDetails(String login, UserDetailsRequest userDetailsRequest) throws LoginNotFoundException, InvalidPropertyException, NullDtoException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		if(userDetailsRequest == null)
			throw new NullDtoException();
		UserDetailsChecker.chechUserDetailsRequestViolations(userDetailsRequest, validator);
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.updateUserAuthorities(..)) && args(login, userAuthoritiesUpdateRequest)")
	public void checkUpdateUserAuthorities(String login, UserAuthoritiesUpdateRequest userAuthoritiesUpdateRequest) throws LoginNotFoundException, NullDtoException, InvalidPropertyException, AuthorityAlreadyExistException, AuthorityNotFoundException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		if(userAuthoritiesUpdateRequest == null)
			throw new NullDtoException();
		chechAuthoritiesUpdateRequestViolations(userAuthoritiesUpdateRequest);
		List<Authority> authorities = userAuthorityRequestMapper.toAuthorities(userAuthoritiesUpdateRequest);
		for(Authority authority : authorities) {
			authorityHolder.isAuthorityExist(authority.getName());
		}
	}
	
	private void chechAuthoritiesUpdateRequestViolations(UserAuthoritiesUpdateRequest userAuthoritiesUpdateRequest) throws InvalidPropertyException {
		Set<ConstraintViolation<UserAuthoritiesUpdateRequest>> violations = validator.validate(userAuthoritiesUpdateRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong authorities update request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}

	@Before("execution(void aq.project.services.UserExtendedService.addUserAuthority(..)) && args(login, authorityRequest)")
	public void checkAddUserAuthority(String login, AuthorityRequest authorityRequest) throws LoginNotFoundException, NullDtoException, InvalidPropertyException, AuthorityAlreadyExistException {
		checkUserAuthorityDetails(login, authorityRequest);
	}
	
	@Before("execution(void aq.project.services.UserExtendedService.revokeUserAuthority(..)) && args(login, authorityRequest)")
	public void checkRevokeUserAuthority(String login, AuthorityRequest authorityRequest) throws LoginNotFoundException, NullDtoException, InvalidPropertyException, AuthorityAlreadyExistException {
		checkUserAuthorityDetails(login, authorityRequest);
	}
	
	private void checkUserAuthorityDetails(String login, AuthorityRequest authorityRequest) throws LoginNotFoundException, NullDtoException, InvalidPropertyException, AuthorityAlreadyExistException {
		LoginChecker.checkUserLoginNotFound(userRepository, login);
		if(authorityRequest == null)
			throw new NullDtoException();
		checkAuthorityRequestViolations(authorityRequest);
		checkAuthorityNotFound(authorityRequest);
	}

	private void checkAuthorityRequestViolations(AuthorityRequest authorityRequest) throws InvalidPropertyException {
		Set<ConstraintViolation<AuthorityRequest>> violations = validator.validate(authorityRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong authority request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}

	private void checkAuthorityNotFound(AuthorityRequest authorityRequest) throws AuthorityAlreadyExistException {
		authorityHolder.isAuthorityExist(authorityRequest.getAuthority());
	}
}

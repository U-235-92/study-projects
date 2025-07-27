package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import aq.project.dto.BasicUserRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.ExtendedUserRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.exceptions.AccessDeniedException;
import aq.project.exceptions.EmptyDtoException;
import aq.project.exceptions.InvalidBasicUserRequestException;
import aq.project.exceptions.InvalidBasicUserResponseException;
import aq.project.exceptions.InvalidExtendedUserRequestException;
import aq.project.exceptions.InvalidExtendedUserResponseException;
import aq.project.exceptions.InvalidStringPropertyException;
import aq.project.exceptions.UserLoginAlreadyExistException;
import aq.project.exceptions.UserLoginNotFoundException;
import aq.project.repositories.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Aspect
@Component
public class UserServiceAspect {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Validator validator;
	
	@Before("execution(void aq.project.services.UserService.basicCreateUser(..)) && args(basicUserRequest)")
	public void checkBasicCreateUser(BasicUserRequest basicUserRequest) throws EmptyDtoException, InvalidBasicUserRequestException, UserLoginAlreadyExistException {
		BasicUserChecker buc = new BasicUserChecker();
		UserLoginChecker ulc = new UserLoginChecker();
		buc.checkEmptyBasicUserRequest(basicUserRequest);
		ulc.checkLoginAlreadyExist(basicUserRequest.getLogin());
		buc.checkBasicUserRequestViolations(basicUserRequest);
	}
	
	@Around("execution(* aq.project.services.UserService.basicReadUser(..)) && args(login, authentication)")
	public BasicUserResponse checkBasicReadUser(ProceedingJoinPoint pjp, String login, Authentication authentication) throws InvalidBasicUserResponseException, AccessDeniedException, Throwable {
		BasicUserChecker buc = new BasicUserChecker();
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(login);
		buc.checkSameLoginBasicUserOperationAccess(login, authentication, "read basic user data");
		BasicUserResponse basicUserResponse = (BasicUserResponse) pjp.proceed(pjp.getArgs());
		buc.checkBasicUserResponseViolations(basicUserResponse);
		return basicUserResponse;
	}
	
	@Before("execution(void aq.project.services.UserService.basicUpdateUser(..)) && args(login, basicUserRequest, authentication)")
	public void checkBasicUpdateUser(String login, BasicUserRequest basicUserRequest, Authentication authentication) throws AccessDeniedException, InvalidBasicUserRequestException, EmptyDtoException, UserLoginNotFoundException, UserLoginAlreadyExistException {
		BasicUserChecker buc = new BasicUserChecker();
		UserLoginChecker ulc = new UserLoginChecker();
		buc.checkEmptyBasicUserRequest(basicUserRequest);
		ulc.checkUserLoginNotFound(login);
		if(!login.equals(basicUserRequest.getLogin()))
			ulc.checkLoginAlreadyExist(basicUserRequest.getLogin());
		buc.checkSameLoginBasicUserOperationAccess(login, authentication, "update user");
		buc.checkBasicUserRequestViolations(basicUserRequest);
	}
	
	@Before("execution(void aq.project.services.UserService.basicDeleteUser(..)) && args(login, authentication)")
	public void checkBasicUserDelete(String login, Authentication authentication) throws UserLoginNotFoundException, AccessDeniedException {
		UserLoginChecker ulc = new UserLoginChecker();
		BasicUserChecker buc = new BasicUserChecker();
		ulc.checkUserLoginNotFound(login);
		buc.checkSameLoginBasicUserOperationAccess(login, authentication, "delete user");
	}
	
	@Before("execution(void aq.project.services.UserService.basicUpdateUserPassword(..)) && args(login, password, authentication)")
	public void checkBasicUpdateUserPassword(String login, String password, Authentication authentication) throws UserLoginNotFoundException, AccessDeniedException, InvalidStringPropertyException {
		UserLoginChecker ulc = new UserLoginChecker();
		BasicUserChecker buc = new BasicUserChecker();
		UserPasswordChecker upc = new UserPasswordChecker();
		ulc.checkUserLoginNotFound(login);
		buc.checkSameLoginBasicUserOperationAccess(login, authentication, "update password");
		upc.checkPassword(password);		
	}
	
	@Before("execution(void aq.project.services.UserService.basicUpdateUserLogin(..)) && args(oldLogin, newLogin, authentication)")
	public void checkBasicUpdateUserLogin(String oldLogin, String newLogin, Authentication authentication) throws UserLoginNotFoundException, AccessDeniedException, InvalidStringPropertyException, UserLoginAlreadyExistException {
		UserLoginChecker ulc = new UserLoginChecker();
		BasicUserChecker buc = new BasicUserChecker();
		ulc.checkUserLoginNotFound(oldLogin);
		ulc.checkLoginAlreadyExist(newLogin);
		buc.checkSameLoginBasicUserOperationAccess(oldLogin, authentication, "update login");
		ulc.checkLogin(newLogin);
	}
	
	@Before("execution(void aq.project.services.UserService.extendedCreateUser(..)) && args(extendedUserRequest)")
	private void checkExtendedCreateUser(ExtendedUserRequest extendedUserRequest) throws EmptyDtoException, InvalidExtendedUserRequestException, UserLoginAlreadyExistException {
		UserLoginChecker ulc = new UserLoginChecker();
		ExtendedUserChecker euc = new ExtendedUserChecker();
		euc.checkEmptyExtendedUserRequest(extendedUserRequest);
		ulc.checkLoginAlreadyExist(extendedUserRequest.getLogin());
		euc.checkExtendedUserRequestViolations(extendedUserRequest);
	}
	
	@Before("execution(void aq.project.services.UserService.extendedUpdateUser(..)) && args(login, extendedUserRequest)")
	private void checkExtendedUpdateUser(String login, ExtendedUserRequest extendedUserRequest) throws UserLoginNotFoundException, UserLoginAlreadyExistException, EmptyDtoException, InvalidExtendedUserRequestException {
		UserLoginChecker ulc = new UserLoginChecker();
		ExtendedUserChecker euc = new ExtendedUserChecker();
		euc.checkEmptyExtendedUserRequest(extendedUserRequest);
		ulc.checkUserLoginNotFound(login);
		if(!login.equals(extendedUserRequest.getLogin()))
			ulc.checkLoginAlreadyExist(extendedUserRequest.getLogin());
		euc.checkExtendedUserRequestViolations(extendedUserRequest);
	}
	
	@Before("execution(void aq.project.services.UserService.extendedDeleteUser(..)) && args(login)")
	private void checkExtendedDeleteUser(String login) throws UserLoginNotFoundException {
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(login);
	}
	
	@Around("execution(* aq.project.services.UserService.extendedReadUser(..)) && args(login)")
	private ExtendedUserResponse checkExtededUserRead(ProceedingJoinPoint pjp, String login) throws UserLoginNotFoundException, InvalidExtendedUserResponseException, Throwable {
		ExtendedUserChecker euc = new ExtendedUserChecker();
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(login);
		ExtendedUserResponse extendedUserResponse = (ExtendedUserResponse) pjp.proceed(pjp.getArgs());
		euc.checkExtendedUserResponseViolations(extendedUserResponse);
		return extendedUserResponse;
	}
	
	@Before("execution(void aq.project.services.UserService.extendedUpdateUserPassword(..)) && args(login, password)")
	private void checkExtendedUpdateUserPassword(String login, String password) throws UserLoginNotFoundException, InvalidStringPropertyException {
		UserLoginChecker ulc = new UserLoginChecker();
		UserPasswordChecker upc = new UserPasswordChecker();
		ulc.checkUserLoginNotFound(login);
		upc.checkPassword(password);
	}
	
	@Before("execution(void aq.project.services.UserService.extendedUpdateUserLogin(..)) && args(oldLogin, newLogin)")
	private void checkExtendedUpdateUserLogin(String oldLogin, String newLogin) throws UserLoginNotFoundException, AccessDeniedException, InvalidStringPropertyException, UserLoginAlreadyExistException {
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(oldLogin);
		ulc.checkLoginAlreadyExist(newLogin);
		ulc.checkLogin(newLogin);
	}
	
	@Before("execution(void aq.project.services.UserService.blockUser(..)) && args(login)")
	private void checkBlockUser(String login) throws UserLoginNotFoundException {
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(login);
	}
	
	@Before("execution(void aq.project.services.UserService.unblockUser(..)) && args(login)")
	private void checkUnblockUser(String login) throws UserLoginNotFoundException {
		UserLoginChecker ulc = new UserLoginChecker();
		ulc.checkUserLoginNotFound(login);
	}
	
	private class UserPasswordChecker {
		
		private void checkPassword(@Size(max = 255) @NotBlank String password) throws InvalidStringPropertyException {
			Set<ConstraintViolation<String>> violations = validator.validate(password);
			if(!violations.isEmpty())
				throw new InvalidStringPropertyException(violations.stream().findFirst().get());
		}
	}
	
	private class UserLoginChecker {
		
		private void checkLoginAlreadyExist(String login) throws UserLoginAlreadyExistException {
			if(userRepository.findByLogin(login).isPresent())
				throw new UserLoginAlreadyExistException(login);
		}
		
		private void checkUserLoginNotFound(String login) throws UserLoginNotFoundException {
			if(userRepository.findByLogin(login).isEmpty())
				throw new UserLoginNotFoundException(login);
		}
		
		private void checkLogin(@Size(max = 255) @NotBlank String login) throws InvalidStringPropertyException {
			Set<ConstraintViolation<String>> violations = validator.validate(login);
			if(!violations.isEmpty())
				throw new InvalidStringPropertyException(violations.stream().findFirst().get());
		}
	}
	
	private class BasicUserChecker {
		
		private void checkEmptyBasicUserRequest(BasicUserRequest basicUserRequest) throws EmptyDtoException {
			if(basicUserRequest == null)
				throw new EmptyDtoException();
		}
		
		private void checkBasicUserRequestViolations(BasicUserRequest basicUserRequest) throws InvalidBasicUserRequestException {
			Set<ConstraintViolation<BasicUserRequest>> violations = validator.validate(basicUserRequest);
			if(!violations.isEmpty())
				throw new InvalidBasicUserRequestException(violations);
		}
		
		private void checkBasicUserResponseViolations(BasicUserResponse basicUserResponse) throws InvalidBasicUserResponseException {
			Set<ConstraintViolation<BasicUserResponse>> violations = validator.validate(basicUserResponse);
			if(!violations.isEmpty()) 
				throw new InvalidBasicUserResponseException(violations);
		}
		
		private void checkSameLoginBasicUserOperationAccess(String login, Authentication authentication, String operation) throws AccessDeniedException {
			if(!login.equals(authentication.getName()))
				throw new AccessDeniedException(operation, authentication.getName());
		}
	}
	
	private class ExtendedUserChecker {
		
		private void checkEmptyExtendedUserRequest(ExtendedUserRequest extendedUserRequest) throws EmptyDtoException {
			if(extendedUserRequest == null)
				throw new EmptyDtoException();
		}
		
		private void checkExtendedUserRequestViolations(ExtendedUserRequest extendedUserRequest) throws InvalidExtendedUserRequestException {
			Set<ConstraintViolation<ExtendedUserRequest>> violations = validator.validate(extendedUserRequest);
			if(!violations.isEmpty())
				throw new InvalidExtendedUserRequestException(violations);
		}
		
		private void checkExtendedUserResponseViolations(ExtendedUserResponse extendedserResponse) throws InvalidExtendedUserResponseException {
			Set<ConstraintViolation<ExtendedUserResponse>> violations = validator.validate(extendedserResponse);
			if(!violations.isEmpty()) 
				throw new InvalidExtendedUserResponseException(violations);
		}
	}
}

package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AccountRequest;
import aq.project.dto.AccountResponse;
import aq.project.dto.EditRequest;
import aq.project.exceptions.AccountAlreadyExistException;
import aq.project.exceptions.AccountRequestException;
import aq.project.exceptions.AccountResponseException;
import aq.project.exceptions.EditAccountRequestException;
import aq.project.repositories.AccountRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountServiceAspect {

	private static final String NULL_LOGIN_MESSAGE = "Login is null";
	
	private final Validator validator;
	private final AccountRepository accountRepository;
	
	@Around("execution(* aq.project.services.AccountService.readAccount(..)) && args(login)")
	public AccountResponse validateReadAccount(ProceedingJoinPoint pjp, String login) throws Throwable, AccountResponseException {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
		AccountResponse accountResponse = (AccountResponse) pjp.proceed();
		Set<ConstraintViolation<AccountResponse>> violations = validator.validate(accountResponse);
		if(!violations.isEmpty())
			throw new AccountResponseException(violations);
		return accountResponse;
	}
	
	@Before("execution(* aq.project.services.AccountService.createAccount(..)) && args(accountRequest)")
	public void validateCreateAccount(AccountRequest accountRequest) {
		NullParameterHandler.handleNullParameter(accountRequest, "Account request is null");
		Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);
		if(!violations.isEmpty())
			throw new AccountRequestException(violations);
		checkAccountAlreadyExist(accountRequest.getLogin());
	}
	
	@Before("execution(* aq.project.services.AccountService.editAccount(..)) && args(login, editRequest)")
	public void validateEditAccount(String login, EditRequest editRequest) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
		NullParameterHandler.handleNullParameter(editRequest, "Edit request is null");
		Set<ConstraintViolation<EditRequest>> violations = validator.validate(editRequest);
		if(!violations.isEmpty())
			throw new EditAccountRequestException(violations);
	}
	
	private void checkAccountAlreadyExist(String login) {
		if(accountRepository.findByLogin(login).isPresent())
			throw new AccountAlreadyExistException(login);
	}
	
	@Before("execution(* aq.project.services.AccountService.deleteAccount(..)) && args(login)")
	public void verifyDeleteAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
	
	@Before("execution(* aq.project.services.AccountService.blockAccount(..)) && args(login)")
	public void verifyBlockAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
	
	@Before("execution(* aq.project.services.AccountService.unblockAccount(..)) && args(login)")
	public void verifyUnblockAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
}

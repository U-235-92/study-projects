package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AccountResponse;
import aq.project.dto.AccountRequest;
import aq.project.dto.EditRequest;
import aq.project.exceptions.AccountAlreadyExistException;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AccountResponseException;
import aq.project.exceptions.CreateAccountRequestException;
import aq.project.exceptions.EditAccountRequestException;
import aq.project.repositories.AccountRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AccountServiceAspect {

	private final Validator validator;
	private final AccountRepository accountRepository;
	
	@Around("execution(* aq.project.services.AccountService.readAccount(..)) && args(login)")
	public AccountResponse validateReadAccount(ProceedingJoinPoint pjp, String login) throws Throwable, NullPointerException, AccountResponseException {
		validateNull(login);
		AccountResponse accountResponse = (AccountResponse) pjp.proceed();
		validateNull(accountResponse);
		Set<ConstraintViolation<AccountResponse>> violations = validator.validate(accountResponse);
		if(!violations.isEmpty())
			throw new AccountResponseException(violations);
		return accountResponse;
	}
	
	@Before("execution(* aq.project.services.AccountService.createAccount(..)) && args(accountRequest)")
	public void validateCreateAccount(AccountRequest accountRequest) {
		validateNull(accountRequest);
		Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);
		if(!violations.isEmpty())
			throw new CreateAccountRequestException(violations);
		validAccountAlreadyExist(accountRequest.getLogin());
	}
	
	@Before("execution(* aq.project.services.AccountService.editAccount(..)) && args(login, editRequest)")
	public void validateEditAccount(String login, EditRequest editRequest) {
		validateNull(login, editRequest);
		Set<ConstraintViolation<EditRequest>> violations = validator.validate(editRequest);
		if(!violations.isEmpty())
			throw new EditAccountRequestException(violations);
		validAccountNotFound(login);
	}
	
	@Before("execution(* aq.project.services.AccountService.deleteAccount(..)) && args(login)")
	public void validateDeleteAccount(String login) {
		validateNull(login);
		validAccountNotFound(login);
	}
	
	private void validateNull(Object... objects) {
		for(Object o : objects) {
			if(o == null) {				
				throw new NullPointerException();
			}
		}
	}
	
	private void validAccountAlreadyExist(String login) {
		if(accountRepository.findByLogin(login).isPresent())
			throw new AccountAlreadyExistException(login);
	}
	
	private void validAccountNotFound(String login) {
		if(accountRepository.findByLogin(login).isEmpty())
			throw new AccountNotFoundException(login);
	}
}

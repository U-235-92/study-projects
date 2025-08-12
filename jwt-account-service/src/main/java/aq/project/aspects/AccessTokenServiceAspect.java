package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AccountRequest;
import aq.project.exceptions.AccessTokenNotFoundException;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AccountRequestException;
import aq.project.repositories.AccessTokenRepository;
import aq.project.repositories.AccountRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessTokenServiceAspect {

	private final Validator validator;
	private final AccountRepository accountRepository;
	private final AccessTokenRepository accessTokenRepository;
	
	@Before("execution(* aq.project.services.AccessTokenService.generateAccessToken(..)) && args(accountRequest)")
	public void validateGenerateAccessToken(AccountRequest accountRequest) {
		Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);
		if(!violations.isEmpty())
			throw new AccountRequestException(violations);
		validAccountNotFound(accountRequest.getLogin());
	}
	
	@Before("execution(* aq.project.services.AccessTokenService.revokeAccessToken(..)) && args(login)")
	public void revokeAccessToken(String login) {
		validAccountNotFound(login);
		validAccessTokenNotFound(login);
	}
	
	@Before("execution(* aq.project.services.AccessTokenService.isValidAccessToken(..)) && args(login,..)")
	public void isValidAccessToken(String login) {
		validAccountNotFound(login);
		validAccessTokenNotFound(login);
	}
	
	private void validAccountNotFound(String login) {
		if(accountRepository.findByLogin(login).isEmpty())
			throw new AccountNotFoundException(login);
	}
	
	private void validAccessTokenNotFound(String login) {
		if(accessTokenRepository.findByLogin(login) == null)
			throw new AccessTokenNotFoundException(login);
	}
}

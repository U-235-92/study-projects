package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AuthenticationRequest;
import aq.project.entities.Account;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AuthenticationRequestException;
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
	
	@Before("execution(* aq.project.services.AccessTokenService.generateAccessToken(..)) && args(authenticationRequest)")
	public void validateGenerateAccessToken(AuthenticationRequest authenticationRequest) {
		Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(authenticationRequest);
		if(!violations.isEmpty())
			throw new AuthenticationRequestException(violations);
	}
	
	@Before("execution(* aq.project.services.AccessTokenService.revokeAccessToken(..)) && args(login)")
	public void validateRevokeAccessToken(String login) {
		checkAccountNotFound(login);
	}
	
	@Before("execution(* aq.project.services.AccessTokenService.isValidAccessToken(..)) && args(login,accessToken)")
	public void validateAccessToken(String login, String accessToken) {
		checkAccountNotFound(login);
	}
	
	private Account checkAccountNotFound(String login) {
		return accountRepository.findByLogin(login).orElseThrow(() -> new AccountNotFoundException(login));
	}
}

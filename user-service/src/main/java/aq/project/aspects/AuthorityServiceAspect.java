package aq.project.aspects;

import java.util.Set;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AuthorityRequest;
import aq.project.exceptions.AuthorityAlreadyExistException;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.exceptions.NullDtoException;
import aq.project.mappers.ViolationToInvalidPropertyMapper;
import aq.project.utils.AuthorityHolder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorityServiceAspect {

	private final Validator validator;
	private final AuthorityHolder authorityHolder;
	
	@Before("execution(void aq.project.services.AuthorityService.createAuthority(..)) && args(authorityRequest)")
	public void checkCreateAuthority(AuthorityRequest authorityRequest) throws NullDtoException, AuthorityAlreadyExistException, InvalidPropertyException {
		checkNullRequest(authorityRequest);
		checkAuthorityRequestViolations(authorityRequest);
		checkAuthorityAlreadyExist(authorityRequest);
	}
	
	@Before("execution(void aq.project.services.AuthorityService.updateAuthority(..)) && args(name, authorityRequest)")
	public void checkUpdateAuthority(String name, AuthorityRequest authorityRequest) throws NullDtoException, InvalidPropertyException, AuthorityAlreadyExistException {
		checkNullRequest(authorityRequest);
		checkAuthorityRequestViolations(authorityRequest);
		checkAuthorityAlreadyExist(authorityRequest);
	}
	
	@Before("execution(void aq.project.services.AuthorityService.deleteAuthority(..)) && args(name)")
	public void checkDeleteAuthority(String name) throws AuthorityNotFoundException {
		checkAuthorityNotFound(name);
	}
	
	private void checkNullRequest(AuthorityRequest authorityRequest) throws NullDtoException {
		if(authorityRequest == null)
			throw new NullDtoException();
	}
	
	private void checkAuthorityRequestViolations(AuthorityRequest authorityRequest) throws InvalidPropertyException {
		Set<ConstraintViolation<AuthorityRequest>> violations = validator.validate(authorityRequest);
		if(!violations.isEmpty())
			throw new InvalidPropertyException("Wrong authority request parameter(s)", violations.stream()
					.map(ViolationToInvalidPropertyMapper::toInvalidProperty).toList());
	}
	
	private void checkAuthorityAlreadyExist(AuthorityRequest authorityRequest) throws AuthorityAlreadyExistException {
		authorityHolder.isAuthorityExist(authorityRequest.getAuthority());
	}
	
	private void checkAuthorityNotFound(String name) throws AuthorityNotFoundException {
		authorityHolder.isAuthorityNotFound(name);
	}
}

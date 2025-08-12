package aq.project.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AccountRequest;

@Aspect
@Component
public class AccessTokenControllerAspect {
	
	private static final String NULL_LOGIN_MESSAGE = "Login is null";
	
	@Before("execution(* aq.project.controllers.AccessTokenController.generateAccessToken(..)) && args(accountRequest)")
	public void verifyGenerateAccessToken(AccountRequest accountRequest) throws Throwable {
		if(accountRequest == null)
			throw new NullPointerException("Account request is null");
	}
	
	@Before("execution(* aq.project.controllers.AccessTokenController.revokeAccessToken(..)) && args(login)")
	public void verifyRevokeAccessToken(String login) {
		handleNullLogin(login);
	}
	
	@Before("execution(* aq.project.controllers.AccessTokenController.checkValidAccessToken(..)) && args(login, accessToken)")
	public void verifyCheckValidAccessToken(String login, String accessToken) {
		handleNullLogin(login);
		if(accessToken == null)
			throw new NullPointerException("Access token is null"); 
	}
	
	private void handleNullLogin(String login) {
		if(login == null || login.toLowerCase().equals("null"))
			throw new NullPointerException(NULL_LOGIN_MESSAGE);
	}
}

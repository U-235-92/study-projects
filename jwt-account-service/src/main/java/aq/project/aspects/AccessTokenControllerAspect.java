package aq.project.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AuthenticationRequest;

@Aspect
@Component
public class AccessTokenControllerAspect {
	
	@Before("execution(* aq.project.controllers.AccessTokenController.generateAccessToken(..)) && args(authenticationRequest)")
	public void validateGenerateAccessToken(AuthenticationRequest authenticationRequest) {
		NullParameterHandler.handleNullParameter(authenticationRequest, "Authentication request is null");
	}
	
	@Before("execution(* aq.project.controllers.AccessTokenController.revokeAccessToken(..)) && args(login)")
	public void validateRevokeAccessToken(String login) {
		NullParameterHandler.handleNullParameter(login, "Login is null");
	}
	
	@Before("execution(* aq.project.controllers.AccessTokenController.checkValidAccessToken(..)) && args(login, accessToken)")
	public void validateCheckValidAccessToken(String login, String accessToken) {
		NullParameterHandler.handleNullParameter(login, "Login is null");
		NullParameterHandler.handleNullParameter(accessToken, "Access token is null");
	}
}

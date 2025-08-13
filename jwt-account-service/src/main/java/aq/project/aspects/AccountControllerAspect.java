package aq.project.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import aq.project.dto.AccountRequest;
import aq.project.dto.EditRequest;

@Aspect
@Component
public class AccountControllerAspect {

	private static final String NULL_LOGIN_MESSAGE = "Login is null";
	
	@Before("execution(* aq.project.controllers.AccountController.readAccount(..)) && args(login)")
	public void verifyReadAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
	
	@Before("execution(* aq.project.controllers.AccountController.createAccount(..)) && args(accountRequest)")
	public void verifyCreateAccount(AccountRequest accountRequest) {
		NullParameterHandler.handleNullParameter(accountRequest, "Account request is null");
	}
	
	@Before("execution(* aq.project.controllers.AccountController.editAccount(..)) && args(editRequest,login)")
	public void verifyEditAccount(String login, EditRequest editRequest) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
		NullParameterHandler.handleNullParameter(editRequest, "Edit request is null");
	}
	
	@Before("execution(* aq.project.controllers.AccountController.deleteAccount(..)) && args(login)")
	public void verifyDeleteAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
	
	@Before("execution(* aq.project.controllers.AccountController.blockAccount(..)) && args(login)")
	public void verifyBlockAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
	
	@Before("execution(* aq.project.controllers.AccountController.unblockAccount(..)) && args(login)")
	public void verifyUnblockAccount(String login) {
		NullParameterHandler.handleNullParameter(login, NULL_LOGIN_MESSAGE);
	}
}

package aq.project.aspects;

import java.util.NoSuchElementException;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import aq.project.entities.User;
import aq.project.exceptions.UserLoginNotFoundException;
import aq.project.repositories.UserRepository;

@Aspect
//@Component
public class UserServiceAspect {

	@Autowired
	private UserRepository userRepository;
	
	@Before("execution(void aq.project.services.UserService.updateUser(..)) && args(user)")
	public void validateCheckUpdateUser(User user) throws UserLoginNotFoundException {
		checkUserLoginExists(user.getLogin());
	}

	@Before("execution(void aq.project.services.UserService.deleteUser(..)) && args(login)")
	public void validateCheckDeleteUser(String login) throws UserLoginNotFoundException {
		checkUserLoginExists(login);
	}

	@Before("execution(void aq.project.services.UserService.changePassword(..)) && args(login,..)")
	public void validateCheckUpdatePassword(String login) throws UserLoginNotFoundException {
		checkUserLoginExists(login);
	}

	private void checkUserLoginExists(String login) throws UserLoginNotFoundException {
		if(userRepository.findByLogin(login).isEmpty())
			throw new UserLoginNotFoundException(getLoginExceptionMessage(login));
	}

	@AfterThrowing(pointcut = "execution(aq.project.entities.User aq.project.services.UserService.getUserByLogin(..)) && args(login)", throwing = "exc")
	public void handleLoginNotFoundException(NoSuchElementException exc, String login) throws UserLoginNotFoundException {
		throw new UserLoginNotFoundException(getLoginExceptionMessage(login));
	}
	
	private String getLoginExceptionMessage(String login) {
		return String.format("User with login %s wasn't found", login);
	}
}

package aq.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.BasicUserRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.ExtendedUserRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;	
	
	@PostMapping("${basic.create.user}")
	public String basicCreateUser(@RequestBody BasicUserRequest basicUserRequest) {
		userService.basicCreateUser(basicUserRequest);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessCreatedUserMessage(basicUserRequest.getLogin());
	}
		
	@GetMapping("${basic.read.user}/{login}")
	public BasicUserResponse basicReadUser(@PathVariable(required = true) String login, Authentication authentication) {
		return userService.basicReadUser(login, authentication);
	}
	
	@PatchMapping("${basic.update.user}/{login}")
	public String basicUpdateUser(@PathVariable(required = true) String login, @RequestBody BasicUserRequest basicUserRequest, Authentication authentication) {
		userService.basicUpdateUser(login, basicUserRequest, authentication);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccesUpdateUserMessage(basicUserRequest.getLogin());
	}
	
	@DeleteMapping("${basic.delete.user}/{login}")
	public String basicDeleteUser(@PathVariable(required = true) String login, Authentication authentication) {
		userService.basicDeleteUser(login, authentication);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessDeleteUserMessage(login);
	}
	
	@PatchMapping("${basic.update.user.password}/{login}")
	public String basicUpdateUserPassword(@PathVariable(required = true) String login, @RequestBody String password, Authentication authentication) {
		userService.basicUpdateUserPassword(login, password, authentication);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessUpdateUserPasswordMessage(login);
	}
	
	@PatchMapping("${basic.update.user.login}/{login}")
	public String basicUpdateUserLogin(@PathVariable(required = true) String oldLogin, @RequestBody String newLogin, Authentication authentication) {
		userService.basicUpdateUserLogin(oldLogin, newLogin, authentication);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessUpdateUserLoginMessage(oldLogin, newLogin);
	}
	
	@PostMapping("${extended.create.user}")
	public String extendedCreateUser(@RequestBody ExtendedUserRequest extendedUserRequest) {
		userService.extendedCreateUser(extendedUserRequest);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessCreatedUserMessage(extendedUserRequest.getLogin());
	}
	
	@PatchMapping("${extended.update.user}/{login}")
	public String extendedUpdateUser(@PathVariable(required = true) String login, @RequestBody ExtendedUserRequest extendedUserRequest) {
		userService.extendedUpdateUser(login, extendedUserRequest);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccesUpdateUserMessage(login);
	}
	
	@DeleteMapping("${extended.delete.user}/{login}")
	public String extendedDeleteUser(@PathVariable(required = true) String login)  {
		userService.extendedDeleteUser(login);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessDeleteUserMessage(login);
	}
	
	@GetMapping("${extended.read.user}/{login}")
	public ExtendedUserResponse extendedReadUser(@PathVariable(required = true) String login) {
		ExtendedUserResponse response = userService.extendedReadUser(login);
		return response;
	}
	
	@GetMapping("${extended.read.all-users}")
	public List<ExtendedUserResponse> extendedReadAllUsers() {
		return userService.extendedReadAllUsers();
	}
	
	@PatchMapping("${extended.update.user.password}/{login}")
	public String extendedUpdateUserPassword(@PathVariable(required = true) String login, @RequestBody String password) {
		userService.extendedUpdateUserPassword(login, password);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessUpdateUserPasswordMessage(login);
	}
	
	@PatchMapping("${extended.update.user.login}/{login}")
	public String extendedUpdateUserLogin(@PathVariable(required = true) String oldLogin, @RequestBody String newLogin) {
		userService.extendedUpdateUserLogin(oldLogin, newLogin);
		SuccesOperationMessageHolder somh = new SuccesOperationMessageHolder();
		return somh.getSuccessUpdateUserLoginMessage(oldLogin, newLogin);
	}
	
	@PatchMapping("${extended.update.user.block}/{login}")
	public String blockUser(@PathVariable(required = true) String login) {
		userService.blockUser(login);
		return String.format("User with login [ %s ] was blocked", login);
	}
	
	@PatchMapping("${extended.update.user.unblock}/{login}")
	public String unblockUser(@PathVariable(required = true) String login) {
		userService.unblockUser(login);
		return String.format("User with login [ %s ] was unblocked", login);
	}
	
	private class SuccesOperationMessageHolder {
		
		private String getSuccessCreatedUserMessage(String login) {
			return String.format("User with login [ %s ] was created", login);
		}
		
		private String getSuccessUpdateUserLoginMessage(String oldLogin, String newLogin) {
			return String.format("User login was updated succesfully [ %s --> %s]", oldLogin, newLogin);
		}
		
		private String getSuccessUpdateUserPasswordMessage(String login) {
			return String.format("Password of user with login [ %s ] was updated succesfully", login);
		}
		
		private String getSuccesUpdateUserMessage(String login) {
			return String.format("User with login [ %s ] was updated succesfully", login);
		}
		
		private String getSuccessDeleteUserMessage(String login) {
			return String.format("User with login [ %s ] was deleted succesfully", login);
		}
	}
}

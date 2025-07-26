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
import aq.project.exceptions.NoValidBasicUserRequestException;
import aq.project.exceptions.NoValidBasicUserResponseException;
import aq.project.exceptions.NoValidExtendedUserRequestException;
import aq.project.exceptions.NoValidExtendedUserResponseException;
import aq.project.exceptions.UserLoginAlreadyExistsException;
import aq.project.exceptions.UserLoginNotFoundException;
import aq.project.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;	
	
	@PostMapping("${basic.create.user}")
	public String basicCreateUser(@RequestBody BasicUserRequest basicUserRequest) throws UserLoginAlreadyExistsException, NoValidBasicUserRequestException {
		throw new UnsupportedOperationException();
	}
		
	@GetMapping("${basic.read.user}/{login}")
	public BasicUserResponse getBasicUserData(@PathVariable(required = true) String login, Authentication authentication) throws UserLoginNotFoundException, NoValidBasicUserResponseException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${basic.update.user}/{login}")
	public String basicUpdateUser(@PathVariable(required = true) String login, @RequestBody BasicUserRequest basicUserRequest, Authentication authentication) throws UserLoginNotFoundException, UserLoginAlreadyExistsException, NoValidBasicUserRequestException {
		throw new UnsupportedOperationException();
	}
	
	@DeleteMapping("${basic.delete.user}/{login}")
	public String basicDeleteUser(@PathVariable(required = true) String login, Authentication authentication) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${basic.update.user.password}/{login}")
	public String basicUpdateUserPassword(@PathVariable(required = true) String login, Authentication authentication) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${basic.update.user.login}/{login}")
	public String basicUpdateUserLogin(@PathVariable(required = true) String login, Authentication authentication) throws UserLoginNotFoundException, UserLoginAlreadyExistsException {
		throw new UnsupportedOperationException();
	}
	
	@PostMapping("${extended.create.user}")
	public String extendedCreateUser(@RequestBody ExtendedUserRequest extendedUserRequest) throws UserLoginAlreadyExistsException, NoValidExtendedUserRequestException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${extended.update.user}/{login}")
	public String extendedUpdateUser(@PathVariable(required = true) String login, @RequestBody ExtendedUserRequest extendedUserRequest) throws UserLoginNotFoundException, UserLoginAlreadyExistsException, NoValidExtendedUserRequestException {
		throw new UnsupportedOperationException();
	}
	
	@DeleteMapping("${extended.delete.user}/{login}")
	public String extendedDeleteUser(@PathVariable(required = true) String login) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("${extended.read.user}/{login}")
	public ExtendedUserResponse extendedReadUser(@PathVariable(required = true) String login) throws UserLoginNotFoundException, NoValidExtendedUserResponseException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("${extended.read.all-users}")
	public List<ExtendedUserResponse> extendedReadAllUsers() throws NoValidExtendedUserResponseException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${extended.update.user.password}/{login}")
	public String extendedUpdateUserPassword(@PathVariable(required = true) String login) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${extended.update.user.login}/{login}")
	public String extendedUpdateUserLogin(@PathVariable(required = true) String login) throws UserLoginNotFoundException, UserLoginAlreadyExistsException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${extended.update.user.block}/{login}")
	public String blockUser(@PathVariable(required = true) String login) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("${extended.update.user.unblock}/{login}")
	public String unblockUser(@PathVariable(required = true) String login) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
}

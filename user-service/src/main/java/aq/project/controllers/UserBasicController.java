package aq.project.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.BasicUserCreationRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.services.UserBasicService;
import lombok.RequiredArgsConstructor;

@Transactional
@RestController
@RequiredArgsConstructor
public class UserBasicController {

	private final UserBasicService userBasicService;
	
	@PostMapping("${basic.create.user}")
	public String createUser(@RequestBody BasicUserCreationRequest basicUserRegistrationRequest) throws AuthorityNotFoundException {
		userBasicService.createUser(basicUserRegistrationRequest);
		return String.format("User with login [ %s ] was created", basicUserRegistrationRequest.getLogin());
	}
		
	@GetMapping("${basic.read.user}/{login}")
	public BasicUserResponse readUser(@PathVariable(required = true) String login, Authentication authentication) {
		return userBasicService.readUser(login, authentication);
	}
	
	@DeleteMapping("${basic.delete.user}/{login}")
	public String deleteUser(@PathVariable(required = true) String login, Authentication authentication) {
		userBasicService.deleteUser(login, authentication);
		return String.format("User with login [ %s ] was deleted succesfully", login);
	}
	
	@PatchMapping("${basic.update.user.password}/{login}")
	public String updateUserPassword(@PathVariable(required = true) String login, @RequestBody PasswordRequest passwordRequest, Authentication authentication) {
		userBasicService.updateUserPassword(login, passwordRequest, authentication);
		return String.format("Password of user with login [ %s ] was updated succesfully", login);
	}
	
	@PatchMapping("${basic.update.user.user-details}/{login}")
	public String updateUserDetails(@PathVariable(required = true) String login, @RequestBody UserDetailsRequest userDetailsRequest, Authentication authentication) {
		userBasicService.updateUserDetails(login, userDetailsRequest, authentication);
		return String.format("User details of user with login [ %s ] was updated successfully", login);
	}
}

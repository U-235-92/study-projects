package aq.project.controllers;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.AuthorityRequest;
import aq.project.dto.ExtendedUserCreationRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.dto.LoginUpdateRequest;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserAuthoritiesUpdateRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.services.UserExtendedService;
import lombok.RequiredArgsConstructor;

@Transactional
@RestController
@RequiredArgsConstructor
public class UserExtendedController {

	private final UserExtendedService userExtendedService;
	
	@PostMapping("${extended.create.user}")
	public String createUser(@RequestBody ExtendedUserCreationRequest extendedUserCreationRequest) throws AuthorityNotFoundException {
		userExtendedService.createUser(extendedUserCreationRequest);
		return String.format("User with login [ %s ] was created", extendedUserCreationRequest.getLogin());
	}
	
	@DeleteMapping("${extended.delete.user}/{login}")
	public String deleteUser(@PathVariable(required = true) String login)  {
		userExtendedService.deleteUser(login);
		return String.format("User with login [ %s ] was deleted succesfully", login);
	}
	
	@GetMapping("${extended.read.user}/{login}")
	public ExtendedUserResponse readUser(@PathVariable(required = true) String login) {
		ExtendedUserResponse response = userExtendedService.readUser(login);
		return response;
	}
	
	@GetMapping("${extended.read.all-users}")
	public List<ExtendedUserResponse> readAllUsers() {
		return userExtendedService.readAllUsers();
	}
	
	@PatchMapping("${extended.update.user.password}/{login}")
	public String updateUserPassword(@PathVariable(required = true) String login, @RequestBody PasswordRequest passwordRequest) {
		userExtendedService.updateUserPassword(login, passwordRequest);
		return String.format("Password of user with login [ %s ] was updated succesfully", login);
	}
	
	@PatchMapping("${extended.update.user.login}")
	public String updateUserLogin(@RequestBody LoginUpdateRequest loginUpdateRequest) {
		userExtendedService.updateUserLogin(loginUpdateRequest);
		return String.format("User login was updated succesfully [ %s --> %s]", loginUpdateRequest.getOldLogin(), loginUpdateRequest.getNewLogin());
	}
	
	@PatchMapping("${extended.update.user.block}/{login}")
	public String blockUser(@PathVariable(required = true) String login) {
		userExtendedService.blockUser(login);
		return String.format("User with login [ %s ] was blocked", login);
	}
	
	@PatchMapping("${extended.update.user.unblock}/{login}")
	public String unblockUser(@PathVariable(required = true) String login) {
		userExtendedService.unblockUser(login);
		return String.format("User with login [ %s ] was unblocked", login);
	}
	
	@PatchMapping("${extended.update.user.user-details}/{login}")
	public String updateUserDetails(@PathVariable(required = true) String login, @RequestBody UserDetailsRequest userDetailsRequest) {
		userExtendedService.updateUserDetails(login, userDetailsRequest);
		return String.format("User details of user with login [ %s ] was updated successfully", login);
	}
	
	@PatchMapping("${extended.update.user.authorities}/{login}")
	public String updateUserAuthorities(@PathVariable(required = true) String login, @RequestBody UserAuthoritiesUpdateRequest userAuthoritiesUpdateRequest) throws AuthorityNotFoundException {
		userExtendedService.updateUserAuthorities(login, userAuthoritiesUpdateRequest);
		return String.format("Authorities of user with login [ %s ] were updated", login);
	}
	
	@PatchMapping("${extended.update.user.authorities.add}/{login}")
	public String addUserAuthority(@PathVariable(required = true) String login, @RequestBody AuthorityRequest authorityRequest) {
		userExtendedService.addUserAuthority(login, authorityRequest);
		return String.format("Authority [ %s ] was added to user with login [ %s ]", authorityRequest.getAuthority(), login);
	}
	
	@PatchMapping("${extended.update.user.authorities.revoke}/{login}")
	public String revokeUserAuthority(@PathVariable(required = true) String login, @RequestBody AuthorityRequest authorityRequest) {
		userExtendedService.revokeUserAuthority(login, authorityRequest);
		return String.format("Authority [ %s ] was revoked from user with login [ %s ]", authorityRequest.getAuthority(), login);
	}
}

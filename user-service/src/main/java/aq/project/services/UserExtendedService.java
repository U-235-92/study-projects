package aq.project.services;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import aq.project.dto.AuthorityRequest;
import aq.project.dto.ExtendedUserCreationRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.dto.LoginUpdateRequest;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserAuthoritiesUpdateRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.entities.Authority;
import aq.project.entities.User;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.mappers.AuthorityRequestToAuthorityMapper;
import aq.project.mappers.ExtendedUserCreationRequestToUserMapper;
import aq.project.mappers.UserAuthorityRequestToAuthorityMapper;
import aq.project.mappers.UserToExtendedUserResponseMapper;
import aq.project.repositories.AuthorityRepository;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserExtendedService {
	
	private final UserRepository userRepository;
	private final PasswordService passwordService;
	private final UserDetailsService userDetailsService;
	private final AuthorityRepository authorityRepository;
	private final AuthorityRequestToAuthorityMapper authorityRequestMapper;
	private final UserAuthorityRequestToAuthorityMapper userAuthorityRequestMapper;
	private final UserToExtendedUserResponseMapper userToExtendedUserResponseMapper;
	private final ExtendedUserCreationRequestToUserMapper extendedUserCreationRequestMapper;
	
	public void createUser(ExtendedUserCreationRequest extendedUserCreationRequest) throws AuthorityNotFoundException {
		User user = extendedUserCreationRequestMapper.toUser(extendedUserCreationRequest);
		long createdAt = Instant.now().toEpochMilli();
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(createdAt);
		userRepository.save(user);
	}

	public void deleteUser(String login) {
		User user = userRepository.findByLogin(login).get();
		userRepository.delete(user);
	}

	public ExtendedUserResponse readUser(String login) {
		User user = userRepository.findByLogin(login).get();
		return userToExtendedUserResponseMapper.toExtendedUserResponse(user);
	}

	public List<ExtendedUserResponse> readAllUsers() {
		List<User> users = userRepository.findAll();
		return userToExtendedUserResponseMapper.toExtendedUserResponseList(users);
	}

	public void updateUserPassword(String login, PasswordRequest passwordRequest) {
		passwordService.updateUserPassword(login, passwordRequest);
	}

	public void updateUserLogin(LoginUpdateRequest loginUpdateRequest) {
		userRepository.updateLogin(loginUpdateRequest.getNewLogin(), loginUpdateRequest.getOldLogin(), Instant.now().toEpochMilli());
	}

	public void blockUser(String login) {
		userRepository.blockUser(login, Instant.now().toEpochMilli());
	}

	public void unblockUser(String login) {
		userRepository.unblockUser(login, Instant.now().toEpochMilli());
	}

	public void updateUserDetails(String login, UserDetailsRequest userDetailsRequest) {
		userDetailsService.updateUserDetails(login, userDetailsRequest);
	}
	
	public void updateUserAuthorities(String login, UserAuthoritiesUpdateRequest userAuthoritiesUpdateRequest) throws AuthorityNotFoundException {
		User user = userRepository.findByLogin(login).get();
		List<Authority> authorities = userAuthorityRequestMapper.toAuthorities(userAuthoritiesUpdateRequest);
		user.setAuthorities(authorities);
		userRepository.save(user);
	}
	
	public void addUserAuthority(String login, AuthorityRequest authorityRequest) throws AuthorityNotFoundException {
		authorityRepository.updateUsersAuthoritiesByLoginAndAuthority(login, authorityRequest.getAuthority());
	}
	
	public void revokeUserAuthority(String login, AuthorityRequest authorityRequest) throws AuthorityNotFoundException {
		User user = userRepository.findByLogin(login).get();
		user.getAuthorities().remove(authorityRequestMapper.toAuthority(authorityRequest));
		userRepository.save(user);
	}
}

package aq.project.services;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import aq.project.dto.BasicUserCreationRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.PasswordRequest;
import aq.project.dto.UserDetailsRequest;
import aq.project.entities.Authority;
import aq.project.entities.User;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.mappers.BasicUserCreationRequestToUserMapper;
import aq.project.mappers.UserToBasicUserResponseMapper;
import aq.project.repositories.UserRepository;
import aq.project.utils.AuthorityHolder;
import aq.project.utils.AuthorityNames;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBasicService {
	
	private final UserRepository userRepository;
	private final AuthorityHolder authorityHolder;
	private final PasswordService passwordService;
	private final UserDetailsService userDetailsService;
	private final UserToBasicUserResponseMapper userToBasicUserResponseMapper;
	private final BasicUserCreationRequestToUserMapper basicUserCreationRequestMapper;
	
	public void createUser(BasicUserCreationRequest basicUserRequest) throws AuthorityNotFoundException {
		User user = basicUserCreationRequestMapper.toUser(basicUserRequest);
		Authority basicRead = authorityHolder.getAuthority(AuthorityNames.BASIC_READ_USER);
		Authority basicUpdate = authorityHolder.getAuthority(AuthorityNames.BASIC_UPDATE_USER);
		Authority basicDelete = authorityHolder.getAuthority(AuthorityNames.BASIC_DELETE_USER);
		user.setAuthorities(List.of(basicRead, basicUpdate, basicDelete));
		long createdAt = Instant.now().toEpochMilli();
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(createdAt);
		userRepository.save(user);
	}

	public BasicUserResponse readUser(String login, Authentication authentication) {
		User user = userRepository.findByLogin(login).get();
		return userToBasicUserResponseMapper.toBasicUserResponse(user);
	}
	
	public void deleteUser(String login, Authentication authentication) {
		userRepository.deleteByLogin(login);
	}
	
	public void updateUserPassword(String login, PasswordRequest passwordRequest, Authentication authentication) {
		passwordService.updateUserPassword(login, passwordRequest);
	}
	
	public void updateUserDetails(String login, UserDetailsRequest userDetailsRequest, Authentication authentication) {
		userDetailsService.updateUserDetails(login, userDetailsRequest);
	}
}

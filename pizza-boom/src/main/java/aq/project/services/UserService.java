package aq.project.services;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import aq.project.dto.BasicUserRequest;
import aq.project.dto.BasicUserResponse;
import aq.project.dto.ExtendedUserRequest;
import aq.project.dto.ExtendedUserResponse;
import aq.project.entities.User;
import aq.project.entities.Authority;
import aq.project.mappers.BasicUserRequestToUserMapper;
import aq.project.mappers.ExtendedUserRequestToUserMapper;
import aq.project.mappers.UserToBasicUserResponseMapper;
import aq.project.mappers.UserToExtendedUserResponseMapper;
import aq.project.repositories.UserRepository;
import aq.project.utils.AuthorityNames;
import aq.project.utils.AuthorityHolder;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthorityHolder userAuthorityHolder;
	private final BasicUserRequestToUserMapper basicUserRequestMapper;
	private final ExtendedUserRequestToUserMapper extendedUserRequestMapper;
	private final UserToBasicUserResponseMapper userToBasicUserResponseMapper;
	private final UserToExtendedUserResponseMapper userToExtendedUserResponseMapper;
	
	public void basicCreateUser(BasicUserRequest basicUserRequest) {
		User user = basicUserRequestMapper.toUser(basicUserRequest);
		Authority basicRead = userAuthorityHolder.getAuthority(AuthorityNames.BASIC_READ_USER);
		Authority basicUpdate = userAuthorityHolder.getAuthority(AuthorityNames.BASIC_UPDATE_USER);
		Authority basicDelete = userAuthorityHolder.getAuthority(AuthorityNames.BASIC_DELETE_USER);
		user.setAuthorities(List.of(basicRead, basicUpdate, basicDelete));
		long createdAt = Instant.now().toEpochMilli();
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(createdAt);
		userRepository.save(user);
	}
	
	public BasicUserResponse basicReadUser(String login, Authentication authentication) {
		User user = userRepository.findByLogin(login).get();
		return userToBasicUserResponseMapper.toBasicUserResponse(user);
	}
	
	public void basicUpdateUser(String login, BasicUserRequest basicUserRequest, Authentication authentication) {
		User user = basicUserRequestMapper.toUser(basicUserRequest);
		user.setUpdatedAt(Instant.now().toEpochMilli());
		userRepository.save(user);
	}
	
	public void basicDeleteUser(String login, Authentication authentication) {
		userRepository.deleteByLogin(login);
	}
	
	public void basicUpdateUserPassword(String login, String password, Authentication authentication) {
		userRepository.updatePassword(login, passwordEncoder.encode(password), Instant.now().toEpochMilli());
	}
	
	public void basicUpdateUserLogin(String oldLogin, String newLogin, Authentication authentication) {
		userRepository.updateLogin(newLogin, oldLogin, Instant.now().toEpochMilli());
	}
	
	public void extendedCreateUser(ExtendedUserRequest extendedUserRequest) {
		User user = extendedUserRequestMapper.toUser(extendedUserRequest);
		long createdAt = Instant.now().toEpochMilli();
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(createdAt);
		userRepository.save(user);
	}
	
	public void extendedUpdateUser(String login, ExtendedUserRequest extendedUserRequest) {
		User user = extendedUserRequestMapper.toUser(extendedUserRequest);
		user.setUpdatedAt(Instant.now().toEpochMilli());
		userRepository.save(user);
	}
	
	public void extendedDeleteUser(String login) {
		userRepository.deleteByLogin(login);
	}
	
	public ExtendedUserResponse extendedReadUser(String login) {
		User user = userRepository.findByLogin(login).get();
		return userToExtendedUserResponseMapper.toExtendedUserResponse(user);
	}
	
	public List<ExtendedUserResponse> extendedReadAllUsers() {
		List<User> users = userRepository.findAll();
		return userToExtendedUserResponseMapper.toExtendedUserResponseList(users);
	}
	
	public void extendedUpdateUserPassword(String login, String password) {
		userRepository.updatePassword(login, passwordEncoder.encode(password), Instant.now().toEpochMilli());
	}
	
	public void extendedUpdateUserLogin(String oldLogin, String newLogin) {
		userRepository.updateLogin(newLogin, oldLogin, Instant.now().toEpochMilli());
	}
	
	public void blockUser(String login) {
		userRepository.blockUser(login, Instant.now().toEpochMilli());
	}
	
	public void unblockUser(@PathVariable(required = true) String login) {
		userRepository.unblockUser(login, Instant.now().toEpochMilli());
	}
}

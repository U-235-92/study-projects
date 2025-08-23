package aq.project.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import aq.project.dtos.UserRequest;
import aq.project.dtos.UserResponse;
import aq.project.entities.User;
import aq.project.exceptions.UserAlreadyExistException;
import aq.project.exceptions.UserNotFoundException;
import aq.project.mappers.UserMapper;
import aq.project.repositories.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public void createUser(@Valid @NotNull UserRequest userRequest) {
		if(userRepository.findByLogin(userRequest.getLogin()).isPresent())
			throw new UserAlreadyExistException(userRequest.getLogin());
		User user = userMapper.toUser(userRequest);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
	}
	
	public UserResponse readUser(@NotBlank String login) {
		User user = userRepository.findByLogin(login).orElseThrow(
				() -> new UserNotFoundException(login));
		return userMapper.toUserResponse(user);
	}
	
	public List<UserResponse> readUsers() {
		return userMapper.toUserResponses(userRepository.findAll());
	}
	
	public void deleteUser(@NotBlank String login) {
		if(userRepository.findByLogin(login).isEmpty())
			throw new UserNotFoundException(login);
		userRepository.deleteByLogin(login);
	}
}

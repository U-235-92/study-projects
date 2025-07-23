package aq.project.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aq.project.entities.User;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().stream().forEach(users::add);
		return users;
	}
	
	@Transactional
	public void createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Transactional
	public void updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Transactional
	public void deleteUser(String username) {
		userRepository.deleteByLogin(username);
	}

	@Transactional
	public void changePassword(String login, String newPassword) {
		userRepository.updatePassword(login, passwordEncoder.encode(newPassword));
	}

	@Transactional
	public boolean userExists(String username) {
		return userRepository.findByLogin(username) != null;
	}
}

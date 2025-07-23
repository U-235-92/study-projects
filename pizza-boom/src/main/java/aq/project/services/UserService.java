package aq.project.services;

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
	public void deleteUser(String login) {
		userRepository.deleteByLogin(login);
	}

	@Transactional
	public void changePassword(String login, String newPassword) {
		userRepository.updatePassword(login, passwordEncoder.encode(newPassword));
	}
	
	@Transactional
	public User getUserById(int id) {
		return userRepository.findById(id).get();
	}
	
	@Transactional
	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login).get();
	}
}

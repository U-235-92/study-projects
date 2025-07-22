package aq.project.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aq.project.entities.User;
import aq.project.entities.UserAuthority;
import aq.project.repositories.UserDetailsRepository;
import aq.project.repositories.UserRepository;

@Service
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);
		if(user == null) {
			StringFormattedMessage sfm = new StringFormattedMessage("User %s does not found", username);
			throw new UsernameNotFoundException(sfm.getFormattedMessage());
		}
		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(user.getLogin())
				.password(user.getPassword())
				.authorities(getAuthorities(user.getAuthorities()))
				.build();
		return userDetails;
	}

	private List<SimpleGrantedAuthority> getAuthorities(List<UserAuthority> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName()))
				.toList();
	}
	
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
		userRepository.updateUser(user);
		userRepository.deleteUserAuthorities(user.getId());
		for(UserAuthority authority : user.getAuthorities()) {
			userRepository.insertUserAuthority(user.getId(), authority.getId());
		}
		userDetailsRepository.updateUserDetails(user.getUserDetails());
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

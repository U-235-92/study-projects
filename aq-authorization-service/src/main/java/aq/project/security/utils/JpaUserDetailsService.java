package aq.project.security.utils;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import aq.project.entities.User;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with login %s wasn't found", username)));
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getLogin())
				.password(user.getPassword())
				.roles(user.getRole().name())
				.build();
	}
}

package aq.project.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import aq.project.entities.User;
import aq.project.entities.UserAuthority;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JPAUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository
				.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User %s does not found", username)));
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
}

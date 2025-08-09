package aq.project.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import aq.project.entities.Account;
import aq.project.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Account with login [ %s ] wasn't found", username)));
		return User.builder()
				.username(account.getLogin())
				.password(account.getPassword())
				.roles(account.getRole().name())
				.build();
	}

}

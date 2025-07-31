package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import aq.project.utils.AuthorityNames;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Profile("dev_db_mem_app")
public class InMemSecurityConfig {
	
	@Bean
	UserDetailsService inMemoryUserDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails alice = org.springframework.security.core.userdetails.User
				.withUsername("alice")
				.password(passwordEncoder.encode("123"))
				.authorities(AuthorityNames.EXTENDED_CREATE_USER, 
						AuthorityNames.EXTENDED_READ_USER, 
						AuthorityNames.EXTENDED_UPDATE_USER, 
						AuthorityNames.EXTENDED_DELETE_USER)
				.build();
		UserDetails alexander = org.springframework.security.core.userdetails.User
				.withUsername("alexander")
				.password(passwordEncoder.encode("321"))
				.authorities(AuthorityNames.BASIC_READ_USER, 
						AuthorityNames.BASIC_UPDATE_USER, 
						AuthorityNames.BASIC_DELETE_USER)
				.build();
		return new InMemoryUserDetailsManager(alice, alexander);
	}
}

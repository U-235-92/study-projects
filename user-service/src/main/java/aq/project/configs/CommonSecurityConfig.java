package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.repositories.UserRepository;
import aq.project.security.JPAAuthenticationProvider;
import aq.project.security.JPAUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class CommonSecurityConfig {

	@Bean
	AuthenticationProvider jpaAuthenticationProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		return new JPAAuthenticationProvider(passwordEncoder, userDetailsService);
	}

	@Bean
	UserDetailsService jpaUserDetailsService(UserRepository userRepository) {
		return new JPAUserDetailsService(userRepository);
	}

	@Bean
	PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

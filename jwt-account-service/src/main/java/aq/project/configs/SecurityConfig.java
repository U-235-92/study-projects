package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import aq.project.repositories.AccountRepository;
import aq.project.security.JpaAuthenticationProvider;
import aq.project.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig {

	private final AccountRepository accountRepository;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(cust -> cust.disable());
		http.authorizeHttpRequests(cust -> cust.anyRequest().permitAll());
		return http.build();
	}
	
	@Bean 
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JpaUserDetailsService jpaUserDetailsService() {
		return new JpaUserDetailsService(accountRepository);
	}
	
	@Bean
	JpaAuthenticationProvider jpaAuthenticationProvider() {
		return new JpaAuthenticationProvider(passwordEncoder(), jpaUserDetailsService());
	}
}

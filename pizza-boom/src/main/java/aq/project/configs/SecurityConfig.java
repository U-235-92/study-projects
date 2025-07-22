package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import aq.project.security.JPAUserDetailsService;

@Configuration
@ComponentScan(basePackages = "aq.project.security")
public class SecurityConfig {

	@Bean
	@Profile(value = "dev_h2")
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(cust -> cust.disable());
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.authorizeHttpRequests(cust -> cust.anyRequest().permitAll());
		return http.build();
	}
	
	@Bean
	@Profile(value = "dev_h2")
	UserDetailsService jpaUserDetailsService() {
		return new JPAUserDetailsService();
	}
	
	@Bean()
	PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

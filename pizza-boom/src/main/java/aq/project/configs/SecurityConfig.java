package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(cust -> cust.disable());
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.authorizeHttpRequests(cust -> cust.anyRequest().permitAll());
		return http.build();
	}
}

package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev_db_h2")
@RequiredArgsConstructor
public class H2SecurityConfig {
	
	@Bean
	SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/h2-console/**");
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers("/h2-console/**").permitAll());
		return http.build();
	}
}

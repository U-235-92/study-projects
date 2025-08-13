package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import aq.project.entities.Role;
import aq.project.repositories.AccountRepository;
import aq.project.security.AccessTokenFilter;
import aq.project.security.JpaAuthenticationProvider;
import aq.project.security.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class SecurityConfig {

	private final AccountRepository accountRepository;
	private final AccessTokenFilter accessTokenFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(cust -> cust.disable());
		http.httpBasic(cust -> cust.disable());
		http.addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/account/delete/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/account/unblock/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/account/block/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/account/edit/*").hasAnyRole(Role.ADMIN.name(), Role.EDITOR.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/account/read/*").hasAnyRole(Role.ADMIN.name(), Role.EDITOR.name(), Role.READER.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/account/create").permitAll());
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/token/revoke-access-token/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/token/check-valid-access-token/*").hasAnyRole(Role.ADMIN.name(), Role.EDITOR.name(), Role.READER.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/token/generate-access-token").permitAll());
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

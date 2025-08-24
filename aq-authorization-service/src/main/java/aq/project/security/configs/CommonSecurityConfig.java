package aq.project.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import aq.project.entities.Role;
import aq.project.repositories.UserRepository;
import aq.project.security.utils.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommonSecurityConfig {
	
	private final UserRepository userRepository;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/user/**", "/client/**");
		
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.httpBasic(Customizer.withDefaults());
		
		http.csrf(cust -> cust.disable());
		
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/user/create").permitAll());
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/user/delete/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/get/*").hasAnyRole(Role.ADMIN.name(), Role.USER.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/get-all").hasAnyRole(Role.ADMIN.name(), Role.USER.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/client/create").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/client/delete/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/client/get/*").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/client/get-all").hasRole(Role.ADMIN.name()));
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		
		return http.build();
	} 
	
	@Bean
	UserDetailsService userDetailsService() {
		return new JpaUserDetailsService(userRepository);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import aq.project.repositories.UserRepository;
import aq.project.security.JPAAuthenticationProvider;
import aq.project.security.JPAUserDetailsService;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/all").hasAuthority("READ_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/id/*").hasAuthority("READ_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers("/h2-console/**").permitAll());
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		http.csrf(cust -> cust.disable());
		return http.build();
	}
	
	@Bean
	AuthenticationProvider jpaAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
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
	
	@Bean
	@Profile("dev_db_mem")
	UserDetailsService inMemoryUserDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails alice = org.springframework.security.core.userdetails.User
				.withUsername("alice")
				.password(passwordEncoder.encode("123"))
				.authorities("READ_USER", "FULL_UPDATE_USER", "DELETE_USER", "CREATE_USER")
				.build();
		UserDetails alexander = org.springframework.security.core.userdetails.User
				.withUsername("alexander")
				.password(passwordEncoder.encode("321"))
				.authorities("READ_USER", "BASIC_UPDATE_USER")
				.build();
		return new InMemoryUserDetailsManager(alice, alexander);
	}
}

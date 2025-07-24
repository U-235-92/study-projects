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
import aq.project.utils.UserAuthorityHolder;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserAuthorityHolder userAuthorityHolder;
	
	@Bean
	@Profile("prod")
	SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/user/**");
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/user/basic/create_user").permitAll());
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/basic/read_user_by_id/*", "/user/basic/read_user_by_login/*").hasAnyAuthority("BASIC_READ_USER", "EXTENDED_READ_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/user/basic/update_user_by_id/*").hasAnyAuthority("BASIC_UPDATE_USER", "EXTENDED_UPDATE_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/user/basic/delete_user_by_id/*").hasAnyAuthority("BASIC_DELETE_USER", "EXTENDED_DELETE_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/user/extended/create_user").hasAuthority("EXTENDED_CREATE_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/user/extended/update_user_by_id/*").hasAuthority("EXTENDED_UPDATE_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/user/extended/delete_user_by_id/*").hasAuthority("EXTENDED_DELETE_USER"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/user/extended/read_user_by_id/*", "/user/extended/read_user_by_login/*", "/extended/read_all_users").hasAuthority("EXTENDED_READ_USER"));
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		http.csrf(cust -> cust.disable());
		return http.build();
	}
			
	@Bean
	@Profile("prod")
	AuthenticationProvider jpaAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
		return new JPAAuthenticationProvider(passwordEncoder, userDetailsService);
	}
	
	@Bean
	@Profile("prod")
	UserDetailsService jpaUserDetailsService(UserRepository userRepository) {
		return new JPAUserDetailsService(userRepository);
	}
	
	@Bean
	@Profile("prod")
	PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Profile("dev_db_h2")
	SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/h2-console/**");
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers("/h2-console/**").permitAll());
		return http.build();
	}
	
	@Bean
	@Profile("dev_db_mem_app")
	UserDetailsService inMemoryUserDetailsService(PasswordEncoder passwordEncoder) {
		UserDetails alice = org.springframework.security.core.userdetails.User
				.withUsername("alice")
				.password(passwordEncoder.encode("123"))
				.authorities("EXTENDED_CREATE_USER", "EXTENDED_READ_USER", "EXTENDED_UPDATE_USER", "EXTENDED_DELETE_USER")
				.build();
		UserDetails alexander = org.springframework.security.core.userdetails.User
				.withUsername("alexander")
				.password(passwordEncoder.encode("321"))
				.authorities("BASIC_READ_USER", "BASIC_UPDATE_USER", "BASIC_DELETE_USER")
				.build();
		return new InMemoryUserDetailsManager(alice, alexander);
	}
}

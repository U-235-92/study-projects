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
import aq.project.utils.AuthorityNames;
import aq.project.utils.EndpointNameHolder;
import aq.project.utils.AuthorityHolder;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {
		
	private final AuthorityHolder authorityHolder; //It will be use when all the authorities will store in a no-mem DB 
	private final EndpointNameHolder endpointNameHolder;
	
	@Bean
	SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/user/**");
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.POST, endpointNameHolder.getEndpoint("basic.create.user"))
				.permitAll());
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.GET, endpointNameHolder.getEndpoint("basic.read.user") + "/*")
				.hasAnyAuthority(AuthorityNames.BASIC_READ_USER, AuthorityNames.EXTENDED_READ_USER)); 
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.PATCH, 
						endpointNameHolder.getEndpoint("basic.update.user") + "/*", 
						endpointNameHolder.getEndpoint("basic.update.user.login") + "/*", 
						endpointNameHolder.getEndpoint("basic.update.user.password") + "/*")
				.hasAnyAuthority(AuthorityNames.BASIC_UPDATE_USER, AuthorityNames.EXTENDED_UPDATE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.DELETE, 
						endpointNameHolder.getEndpoint("basic.delete.user") + "/*")
				.hasAnyAuthority(AuthorityNames.BASIC_DELETE_USER, AuthorityNames.EXTENDED_DELETE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.POST, 
						endpointNameHolder.getEndpoint("extended.create.user"))
				.hasAuthority(AuthorityNames.EXTENDED_CREATE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.PATCH, 
						endpointNameHolder.getEndpoint("extended.update.user") + "/*", 
						endpointNameHolder.getEndpoint("extended.update.user.login") + "/*", 
						endpointNameHolder.getEndpoint("extended.update.user.password") + "/*", 
						endpointNameHolder.getEndpoint("extended.update.user.block") + "/*", 
						endpointNameHolder.getEndpoint("extended.update.user.unblock") + "/*")
				.hasAuthority(AuthorityNames.EXTENDED_UPDATE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.DELETE, 
						endpointNameHolder.getEndpoint("extended.delete.user") + "/*")
				.hasAuthority(AuthorityNames.EXTENDED_DELETE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.GET, 
						endpointNameHolder.getEndpoint("extended.read.user") + "/*", 
						endpointNameHolder.getEndpoint("extended.read.all-users"))
				.hasAuthority(AuthorityNames.EXTENDED_READ_USER));
		http.authorizeHttpRequests(cust -> cust
				.anyRequest()
				.authenticated());
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

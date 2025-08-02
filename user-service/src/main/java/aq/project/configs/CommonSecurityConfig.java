package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import aq.project.repositories.UserRepository;
import aq.project.security.AuthenticationExceptionEntryPoint;
import aq.project.security.BlockUserFilter;
import aq.project.security.JPAAuthenticationProvider;
import aq.project.security.JPAUserDetailsService;
import aq.project.utils.AuthorityNames;
import aq.project.utils.EndpointNameHolder;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = false)
public class CommonSecurityConfig {

	private final BlockUserFilter blockUserFilter;
	private final EndpointNameHolder endpointNameHolder;
	
	@Bean
	SecurityFilterChain commonScurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/user/**", "/authority/**");
		http.csrf(cust -> cust.disable());
		http.httpBasic(Customizer.withDefaults());
		http.addFilterBefore(blockUserFilter, AuthorizationFilter.class);
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(cust -> cust.authenticationEntryPoint(new AuthenticationExceptionEntryPoint()));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.POST, 
						endpointNameHolder.getEndpoint("basic.create.user"))
				.permitAll());
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.GET, 
						endpointNameHolder.getEndpoint("basic.read.user") + "/*")
				.hasAnyAuthority(AuthorityNames.BASIC_READ_USER, AuthorityNames.EXTENDED_READ_USER)); 
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.PATCH, 
						endpointNameHolder.getEndpoint("basic.update.user") + "/**")
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
				.requestMatchers(HttpMethod.POST, 
						endpointNameHolder.getEndpoint("authority") + "/**")
				.hasAuthority(AuthorityNames.CREATE_AUTHORITY));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.PATCH, 
						endpointNameHolder.getEndpoint("extended.update.user") + "/**")
				.hasAuthority(AuthorityNames.EXTENDED_UPDATE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.PATCH,
						endpointNameHolder.getEndpoint("authority") + "/**")
				.hasAuthority(AuthorityNames.UPDATE_AUTHORITY));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.DELETE, 
						endpointNameHolder.getEndpoint("extended.delete.user") + "/*")
				.hasAuthority(AuthorityNames.EXTENDED_DELETE_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.DELETE, 
						endpointNameHolder.getEndpoint("authority") + "/**")
				.hasAuthority(AuthorityNames.DELETE_AUTHORITY));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.GET, 
						endpointNameHolder.getEndpoint("extended.read.user") + "/*", 
						endpointNameHolder.getEndpoint("extended.read.all-users"))
				.hasAuthority(AuthorityNames.EXTENDED_READ_USER));
		http.authorizeHttpRequests(cust -> cust
				.requestMatchers(HttpMethod.GET, 
						endpointNameHolder.getEndpoint("authority") + "/**")
				.hasAuthority(AuthorityNames.READ_AUTHORITY));
		http.authorizeHttpRequests(cust -> cust
				.anyRequest()
				.authenticated());
		return http.build();
	}
	
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

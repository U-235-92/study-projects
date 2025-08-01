package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import aq.project.security.AuthenticationExceptionEntryPoint;
import aq.project.security.BlockUserFilter;
import aq.project.utils.AuthorityNames;
import aq.project.utils.EndpointNameHolder;
import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev_db_h2")
@RequiredArgsConstructor
public class H2SecurityConfig {

	private final BlockUserFilter blockUserFilter;
	private final EndpointNameHolder endpointNameHolder;
	
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/user/**", "/authority/**", "/h2-console/**");
		http.addFilterBefore(blockUserFilter, AuthorizationFilter.class);
		http.headers(cust -> cust.frameOptions(opt -> opt.sameOrigin()));
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(cust -> cust.authenticationEntryPoint(new AuthenticationExceptionEntryPoint()));
		http.authorizeHttpRequests(cust -> cust.requestMatchers("/h2-console/**").permitAll());
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
		http.httpBasic(Customizer.withDefaults());
		http.csrf(cust -> cust.disable());
		return http.build();
	}
}

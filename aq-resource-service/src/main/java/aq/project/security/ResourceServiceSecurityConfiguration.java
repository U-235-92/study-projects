package aq.project.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import aq.project.mapper.JwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ResourceServiceSecurityConfiguration {

	@Value("${aq.resource-service.jwk-set-uri}")
	private String jwkSetUri;
	
	private final JwtAuthenticationConverter converter;
	
	@Bean
	SecurityFilterChain commonSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(cust -> cust.disable());
		
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.oauth2ResourceServer(cust -> cust.jwt(jwt -> jwt.jwkSetUri(jwkSetUri)));
		http.oauth2ResourceServer(cust -> cust.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)));
		http.oauth2ResourceServer(Customizer.withDefaults());
		
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/resource/read-message/**").hasAuthority("read_msg"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.POST, "/resource/write-message").hasAuthority("write_msg"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.PATCH, "/resource/edit-message/**").hasAuthority("edit_msg"));
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.DELETE, "/resource/delete-message/**").hasAuthority("delete_msg"));
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		
		return http.build();
	}
}

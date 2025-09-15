package aq.project.service;

import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

	public String sayHello(Authentication authentication) {
		String authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining("\s"));
		return String.format("Hello, %s! Your authorities: " + authorities, authentication.getName());
	}
}

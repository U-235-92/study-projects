package aq.project.security_tests.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithCustomUser customUser) {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		List<SimpleGrantedAuthority> authorities = Arrays.stream(customUser.authorities()).map(SimpleGrantedAuthority::new).toList();
		Authentication authentication = new UsernamePasswordAuthenticationToken(customUser.login(), customUser.password(), authorities);
		securityContext.setAuthentication(authentication);
		return securityContext;
	}

}

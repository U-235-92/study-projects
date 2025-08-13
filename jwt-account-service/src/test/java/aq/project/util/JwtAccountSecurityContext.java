package aq.project.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import aq.project.entities.Role;
import aq.project.security.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;

public class JwtAccountSecurityContext implements WithSecurityContextFactory<WithJwtAccount> {

	@Override
	public SecurityContext createSecurityContext(WithJwtAccount annotation) {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		Map<String, Object> map = new HashMap<>();
		map.put(Claims.SUBJECT, annotation.login());
		map.put("token-revoked", false);
		map.put("acc-role", Role.READER.name());
		Claims claims = new DefaultClaims(map);
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(claims);
		securityContext.setAuthentication(authenticationToken);
		return securityContext;
	}
}

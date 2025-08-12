package aq.project.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;

public class JwtAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private boolean isBlocked;
	private List<? extends GrantedAuthority> roles;
	
	public JwtAuthenticationToken(Claims claims) {
		roles = List.of(new SimpleGrantedAuthority(claims.get("role").toString()));
		login = claims.getSubject();
	}
	
	@Override
	public String getName() {
		return login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return login;
	}

	@Override
	public boolean isAuthenticated() {
		return isBlocked;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		isBlocked = isAuthenticated;
	}
}

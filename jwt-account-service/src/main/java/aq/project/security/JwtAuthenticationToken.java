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
	private boolean isRevoked;
	private List<? extends GrantedAuthority> roles;
	
	public JwtAuthenticationToken(Claims claims) {
		login = claims.getSubject();
		isRevoked = (boolean) claims.get("token-revoked"); 
		String role = "ROLE_" + claims.get("acc-role").toString();
		roles = List.of(new SimpleGrantedAuthority(role));
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
		return isRevoked;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
}

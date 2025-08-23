package aq.project.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class ResourceServiceAuthenticationToken extends JwtAuthenticationToken {

	private static final long serialVersionUID = 1L;
	
	private Collection<? extends GrantedAuthority> authorities;
	private String author;
	
	public ResourceServiceAuthenticationToken(Jwt jwt) {
		super(jwt);
		authorities = jwt.getClaimAsStringList("scope").stream().map(SimpleGrantedAuthority::new).toList();
		author = jwt.getSubject();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<GrantedAuthority> getAuthorities() {
		return (Collection<GrantedAuthority>) authorities;
	}
	
	public String getAuthor() {
		return author;
	}
}

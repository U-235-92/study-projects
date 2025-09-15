package aq.project.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final JwtEncoder jwtEncoder;
	private final UserDetailsService userDetailsService;
	
	public String generateToken(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if(!userDetails.getPassword().equals(userDetails.getPassword()))
			throw new IllegalArgumentException("Wrong password was inputted for user: " + username);
		Instant issuedAt = Instant.now();
		Instant expAt = issuedAt.plus(1, ChronoUnit.HOURS);
		List<String> scope = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("jwt-spring-service")
				.subject(username)
				.issuedAt(issuedAt)
				.expiresAt(expAt)
				.claim("scope", scope)
				.build();
		JwtEncoderParameters params = JwtEncoderParameters.from(claims);
		return jwtEncoder.encode(params).getTokenValue();
	}
}

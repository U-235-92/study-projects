package aq.project.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import aq.project.entities.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.signature.key}")
	private String jwtKeyString;
	@Value("${jwt.acces-token.ttl}")
	private Duration jwtAccessTokenTTL;
	
	public String generateAccessToken(Account account) {
		Instant accessTokenExp = LocalDateTime.now()
				.plus(jwtAccessTokenTTL)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		return Jwts.builder()
				.subject(account.getLogin())
				.expiration(Date.from(accessTokenExp))
				.claim("role", account.getRole().name())
				.claim("revoked", false)
				.signWith(getSecretKey())
				.compact();
	}
	
	public Claims getAccessTokenClaims(String accessToken) {
		try {			
			return Jwts.parser()
					.verifyWith(getSecretKey())
					.build()
					.parseSignedClaims(accessToken)
					.getPayload();
		} catch(Exception exc) {			
			return null;
		}
	}
	
	public String revokeAccessToken(String accessToken) {
		Claims claims = getAccessTokenClaims(accessToken);
		claims.put("revoked", true);
		return Jwts.builder()
				.subject(claims.getSubject())
				.expiration(claims.getExpiration())
				.claims(claims)
				.signWith(getSecretKey())
				.compact();
	}
	
	public boolean isValidToken(String token) {
		try {			
			Jwts.parser().verifyWith(getSecretKey()).build();
			return true;
		} catch(Exception exc) {
			return false;
		}
	}
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtKeyString));
	}
}

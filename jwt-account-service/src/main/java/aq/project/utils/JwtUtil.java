package aq.project.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import aq.project.entities.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.acces-token.ttl}")
	private Duration jwtAccessTokenTTL;
	
	private final SecretKey secretKey;
	
	public JwtUtil(@Value("${jwt.signature.key}") String jwtKeyString) {
		secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtKeyString));
	}

	public String generateAccessToken(Account account) {
		LocalDateTime now = LocalDateTime.now();
		Instant accessTokenExp = now
				.plus(jwtAccessTokenTTL)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		Date expiration = Date.from(accessTokenExp);
		return Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				.signWith(secretKey)
				.issuer("account-service")
				.subject(account.getLogin())
				.issuedAt(issuedAt)
				.expiration(expiration)
				.claim("role", account.getRole().name())
				.claim("revoked", false)
				.compact();
	}
	
	public String revokeAccessToken(String accessToken) {
		Claims claims = getAccessTokenClaims(accessToken);
		return Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				.signWith(secretKey)
				.issuer(claims.getIssuer())
				.subject(claims.getSubject())
				.issuedAt(claims.getIssuedAt())
				.expiration(claims.getExpiration())
				.claim("role", claims.get("role"))
				.claim("revoked", true)
				.compact();
	}
	
	public boolean isValidToken(String accessToken) {
		try {	
			if(accessToken == null)
				return false;
			Claims claims = getAccessTokenClaims(accessToken);
			if(claims.get("revoked").equals(true))
				return false;
			return true;
		} catch(Exception exc) {
			return false;
		}
	}
	
	public Claims getAccessTokenClaims(String accessToken) {
		try {			
			return Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(accessToken)
					.getPayload();
		} catch(Exception exc) {			
			return null;
		}
	}
}

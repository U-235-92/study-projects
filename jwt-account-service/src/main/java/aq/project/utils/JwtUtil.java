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
				.claim("acc-role", account.getRole().name())
				.claim("acc-not-blocked", account.isNotBlocked())
				.claim("token-revoked", false)
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
				.claim("acc-role", claims.get("acc-role"))
				.claim("acc-not-blocked", claims.get("acc-not-blocked"))
				.claim("token-revoked", true)
				.compact();
	}
	
	public boolean isValidToken(String accessToken) {
		try {	
			if(accessToken == null)
				return false;
			Claims claims = getAccessTokenClaims(accessToken);
			if(claims.get("token-revoked").equals(true))
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

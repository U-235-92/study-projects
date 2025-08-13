package aq.project.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import aq.project.entities.Account;
import aq.project.repositories.AccountRepository;
import aq.project.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	private final AccountRepository accountRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = getAccessToken(request);
		if(accessToken != null && jwtUtil.isValidToken(accessToken)) {
			String login = jwtUtil.getAccessTokenClaims(accessToken).getSubject();
			boolean isNotBlockedAccount = isAccountNotBlockedInJwt(accessToken) && isAccountNotBlockedInDatabase(login);
			if(isNotBlockedAccount == false) {
				response.sendError(HttpStatus.FORBIDDEN.value(), String.format("Account with login [ %s ] is blocked", login));
				return;
			} else {				
				JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwtUtil.getAccessTokenClaims(accessToken));
				SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
			}
		}
		doFilter(request, response, filterChain);
	}
	
	private String getAccessToken(HttpServletRequest request) {
		final String AUTHORIZATION = "Authorization";
		final String BEARER = "Bearer ";
		if(request.getHeader(AUTHORIZATION ) != null && request.getHeader(AUTHORIZATION).startsWith(BEARER))
			return request.getHeader(AUTHORIZATION).substring(BEARER.length());
		return null;
	}
	
	private boolean isAccountNotBlockedInJwt(String accessToken) {
		return (boolean) jwtUtil.getAccessTokenClaims(accessToken).get("acc-not-blocked");
	}
	
	private boolean isAccountNotBlockedInDatabase(String login) {
		Optional<Account> optAccount = accountRepository.findByLogin(login);
		if(optAccount.isEmpty())
			return false;
		Account account = optAccount.get();
		return account.isNotBlocked() == true;
	}
}

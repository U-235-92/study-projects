package aq.project.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String accessToken = getAccessToken(request);
		if(accessToken != null && jwtUtil.isValidToken(accessToken)) {
			JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwtUtil.getAccessTokenClaims(accessToken));
			SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
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
}

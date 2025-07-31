package aq.project.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import aq.project.entities.User;
import aq.project.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BlockUserFilter extends OncePerRequestFilter {
	
	private final UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Decoder decoder = Base64.getDecoder();
		if(request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
			String authorization = new String(decoder.decode(request.getHeader(HttpHeaders.AUTHORIZATION).substring(6).trim()));
			Optional<User> optUser = userRepository.findByLogin(authorization.split(":")[0]);
			if(optUser.isPresent()) {
				boolean isNotBanned = optUser.get().isNotBanned();
				if(isNotBanned) {
					filterChain.doFilter(request, response);
				} else {
					response.sendError(HttpStatus.FORBIDDEN.value(), 
							String.format("Access denied. User with login [ %s ] is blocked", optUser.get().getLogin()));
				}
			} 
		} else {
			filterChain.doFilter(request, response);
		}	
	}
}

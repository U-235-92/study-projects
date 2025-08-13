package aq.project.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aq.project.dto.AuthenticationRequest;
import aq.project.entities.AccessToken;
import aq.project.entities.Account;
import aq.project.exceptions.AccessTokenNotFoundException;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.BlockedAccountException;
import aq.project.repositories.AccessTokenRepository;
import aq.project.repositories.AccountRepository;
import aq.project.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccessTokenService {
	
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;
	private final AccessTokenRepository accessTokenRepository;
	
	public String generateAccessToken(AuthenticationRequest authenticationRequest) {
		Account account = findAccountOrThrow(authenticationRequest.getLogin());
		if(isAccountBlocked(account))
			throw new BlockedAccountException(account.getLogin());
		if(isNotValidPassword(authenticationRequest.getPassword(), account.getPassword()))
			throw new BadCredentialsException(String.format("Input password for account [ %s ] is incorrect", authenticationRequest.getLogin()));
		AccessToken accessToken = new AccessToken(account, jwtUtil.generateAccessToken(account));
		accessTokenRepository.deleteByLogin(authenticationRequest.getLogin());
		accessTokenRepository.save(accessToken);
		return accessToken.getJwt();
	}
	
	private Account findAccountOrThrow(String login) {
		return accountRepository.findByLogin(login)
				.orElseThrow(() -> new AccountNotFoundException(String.format("Account with login [ %s ] wasn't found", login)));
	}
	
	private boolean isAccountBlocked(Account account) {
		return account.isNotBlocked() == false;
	}
	
	private boolean isNotValidPassword(String rawRequestPassword, String encodedPassword) {
		return passwordEncoder.matches(rawRequestPassword, encodedPassword) == false;
	}
	
	public void revokeAccessToken(String login) {
		AccessToken accessToken = findAccessTokenOrThrow(login);
		String jwt = jwtUtil.revokeAccessToken(accessToken.getJwt());
		if(jwt != null) {			
			accessToken.setJwt(jwt);
			accessTokenRepository.save(accessToken);
		}
	}
	
	public boolean isValidAccessToken(String login, String accessToken) {
		AccessToken storedAccessToken = findAccessTokenOrThrow(login); 
		String storedJwt = storedAccessToken.getJwt();
		if(accessToken.equals(storedJwt)) 			
			return jwtUtil.isValidToken(accessToken);
		return false;
	}
	
	private AccessToken findAccessTokenOrThrow(String login) {
		return accessTokenRepository.findByLogin(login)
				.orElseThrow(() -> new AccessTokenNotFoundException(login));
	}
}

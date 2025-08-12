package aq.project.services;

import org.springframework.stereotype.Service;

import aq.project.dto.AccountRequest;
import aq.project.entities.AccessToken;
import aq.project.entities.Account;
import aq.project.mappers.AccountMapper;
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
	private final AccountMapper accountMapper;
	private final AccountRepository accountRepository;
	private final AccessTokenRepository accessTokenRepository;
	
	public String generateAccessToken(AccountRequest accountRequest) {
		Account account = accountRepository.findByLogin(accountRequest.getLogin()).get();
		AccessToken accessToken = new AccessToken(account, jwtUtil.generateAccessToken(accountMapper.toAccount(accountRequest)));
		accessTokenRepository.deleteByLogin(accountRequest.getLogin());
		accessTokenRepository.save(accessToken);
		return accessToken.getJwt();
	}
	
	public void revokeAccessToken(String login) {
		AccessToken accessToken = accessTokenRepository.findByLogin(login);
		String jwt = jwtUtil.revokeAccessToken(accessToken.getJwt());
		if(jwt != null) {			
			accessToken.setJwt(jwt);
			accessTokenRepository.save(accessToken);
		}
	}
	
	public boolean isValidAccessToken(String login, String accessToken) {
		AccessToken storedAccessToken = accessTokenRepository.findByLogin(login); 
		String storedJwt = storedAccessToken.getJwt();
		if(accessToken.equals(storedJwt)) {				
			return jwtUtil.isValidToken(accessToken);
		}
		return false;
	}
}

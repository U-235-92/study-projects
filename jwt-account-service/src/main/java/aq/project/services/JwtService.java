package aq.project.services;

import org.springframework.stereotype.Service;

import aq.project.dto.AccountRequest;
import aq.project.mappers.AccountMapper;
import aq.project.repository.JwtRepository;
import aq.project.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {
	
	private final JwtUtil jwtUtil;
	private final JwtRepository jwtRepository;
	private final AccountMapper accountMapper;
	
	public String generateAccessToken(AccountRequest accountRequest) {
		String accessToken = jwtUtil.generateAccessToken(accountMapper.toAccount(accountRequest));
		jwtRepository.saveAccessToken(accountRequest.getLogin(), accessToken);
		return accessToken;
	}
	
	public void revokeAccessToken(String login) {
		String accessToken = jwtRepository.findAccessTokenById(login);
		accessToken = jwtUtil.revokeAccessToken(accessToken);
		jwtRepository.updateAccessToken(login, accessToken);
	}
}

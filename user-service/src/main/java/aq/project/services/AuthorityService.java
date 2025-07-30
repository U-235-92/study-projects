package aq.project.services;

import java.util.List;

import org.springframework.stereotype.Service;

import aq.project.dto.AuthorityRequest;
import aq.project.dto.AuthorityResponse;
import aq.project.entities.Authority;
import aq.project.mappers.AuthorityRequestToAuthorityMapper;
import aq.project.mappers.AuthorityToAuthorityResponseMapper;
import aq.project.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

	private final AuthorityRepository authorityRepository;
	private final AuthorityToAuthorityResponseMapper authorityMapper;
	private final AuthorityRequestToAuthorityMapper authorityRequestMapper;
	
	public void createAuthority(AuthorityRequest authorityRequest) {
		Authority authority = authorityRequestMapper.toAuthority(authorityRequest);
		authorityRepository.save(authority);
	}
	
	public void updateAuthority(String name, AuthorityRequest authorityRequest) {
		Authority authority = authorityRepository.findByName(name).get();
		authority.setName(authorityRequestMapper.toAuthority(authorityRequest).getName());
		authorityRepository.save(authority);
	}
	
	public void deleteAuthority(String name) {
		authorityRepository.deleteByName(name);
	}
	
	public List<AuthorityResponse> findAllAuthorities() {
		return authorityMapper.toAuthorityResponse(authorityRepository.findAll());
	}
}

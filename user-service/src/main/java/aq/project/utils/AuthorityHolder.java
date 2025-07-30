package aq.project.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import aq.project.entities.Authority;
import aq.project.exceptions.AuthorityAlreadyExistException;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityHolder {

	private final AuthorityRepository authorityRepository;
	
	public Authority getAuthority(String authority) throws AuthorityNotFoundException {
		Optional<Authority> optAuthority = authorityRepository.findByName(authority);
		return optAuthority
				.orElseThrow(() -> new AuthorityNotFoundException(authority));
	}
	
	public List<Authority> getUserAuthorities(String login) {
		return authorityRepository.findUserAuthorities(login);
	}
	
	@SuppressWarnings("unused")
	public boolean isAuthorityExist(String name) throws AuthorityAlreadyExistException {
		Optional<Authority> optAuthority = authorityRepository.findByName(name);
		return optAuthority
				.map(authority -> true)
				.orElseThrow(() -> new AuthorityAlreadyExistException(name));
	}
	
	@SuppressWarnings("unused")
	public boolean isAuthorityNotFound(String name) throws AuthorityNotFoundException {
		Optional<Authority> optAuthority = authorityRepository.findByName(name);
		return optAuthority
				.map(authority -> true)
				.orElseThrow(() -> new AuthorityNotFoundException(name));
	}
}

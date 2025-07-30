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
	
	public boolean isAuthorityExist(String name) throws AuthorityAlreadyExistException {
		Optional<Authority> optAuthority = authorityRepository.findByName(name);
		if(optAuthority.isPresent())
			throw new AuthorityAlreadyExistException(name);
		return false;
	}
	
	public boolean isAuthorityNotFound(String name) throws AuthorityNotFoundException {
		Optional<Authority> optAuthority = authorityRepository.findByName(name);
		if(optAuthority.isEmpty())
			throw new AuthorityNotFoundException(name);
		return false;
	}
}

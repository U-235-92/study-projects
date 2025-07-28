package aq.project.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aq.project.entities.Authority;
import aq.project.exceptions.UnknownPropertyException;
import aq.project.repositories.AuthorityRepository;

@Component
public class AuthorityHolder {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	public String getAuthorityName(String authority) throws UnknownPropertyException {
		Optional<Authority> optAuthority = authorityRepository.findAuthorityByName(authority);
		return optAuthority
				.map(Authority::getName)
				.orElseThrow(() -> new UnknownPropertyException(String.format("Unknown user authority: %s", authority)));
	}
	
	public Authority getAuthority(String authority) throws UnknownPropertyException {
		Optional<Authority> optAuthority = authorityRepository.findAuthorityByName(authority);
		return optAuthority
				.orElseThrow(() -> new UnknownPropertyException(String.format("Unknown user authority: %s", authority)));
	}
}

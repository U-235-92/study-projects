package aq.project.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aq.project.entities.UserAuthority;
import aq.project.exceptions.UnknownPropertyException;
import aq.project.repositories.UserAuthorityRepository;

@Component
public class UserAuthorityHolder {

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	public String getAuthorityName(String authority) throws UnknownPropertyException {
		Optional<UserAuthority> optAuthority = userAuthorityRepository.findAuthorityByName(authority);
		return optAuthority
				.map(UserAuthority::getName)
				.orElseThrow(() -> new UnknownPropertyException(String.format("Unknown user authority: %s", authority)));
	}
}

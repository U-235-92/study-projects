package aq.project.utils;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.Properties;

import aq.project.entities.UserAuthority;
import aq.project.exceptions.UnknownUserAuthorityException;
import aq.project.repositories.UserAuthorityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAuthorityHolder {

	private final UserAuthorityRepository userAuthorityRepository;
	private final Properties exceptionMessagesHolder;
	
	public String getAuthorityName(String authority) throws UnknownUserAuthorityException {
		Optional<UserAuthority> optAuthority = userAuthorityRepository.findAuthorityByName(authority);
		return optAuthority
				.map(UserAuthority::getName)
				.orElseThrow(() -> new UnknownUserAuthorityException(MessageFormat.format(exceptionMessagesHolder.getProperty("authority.unknown-authority"), authority)));
	}
}

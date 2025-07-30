package aq.project.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import aq.project.dto.PasswordRequest;
import aq.project.mappers.PasswordRequestToEncodedPasswordMapper;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordService {

	private final UserRepository userRepository;
	private final PasswordRequestToEncodedPasswordMapper passwordRequestMapper;
	
	public void updateUserPassword(String login, PasswordRequest passwordRequest) {
		userRepository.updatePassword(login, passwordRequestMapper.toEncodedPassword(passwordRequest), Instant.now().toEpochMilli());
	}
}

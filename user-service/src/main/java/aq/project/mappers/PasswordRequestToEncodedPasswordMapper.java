package aq.project.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import aq.project.dto.PasswordRequest;

@Component
public class PasswordRequestToEncodedPasswordMapper {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String toEncodedPassword(PasswordRequest passwordRequest) {
		return passwordEncoder.encode(passwordRequest.getPassword());
	}
}

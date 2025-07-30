package aq.project.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import aq.project.dto.UserDetailsRequest;
import aq.project.entities.UserDetails;
import aq.project.mappers.UserDetailsRequestToUserDetailsMapper;
import aq.project.repositories.UserDetailsRepository;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
	
	private final UserRepository userRepository;
	private final UserDetailsRepository userDetailsRepository;
	private final UserDetailsRequestToUserDetailsMapper userDetailsRequestMapper;
	
	public void updateUserDetails(String login, UserDetailsRequest userDetailsRequest) {
		UserDetails origUserDetails = userRepository.findUserDetailsByUserLogin(login);
		UserDetails requestUserDetails = userDetailsRequestMapper.toUserDetails(userDetailsRequest);
		origUserDetails.setBirthDate(requestUserDetails.getBirthDate());
		origUserDetails.setEmail(requestUserDetails.getEmail());
		origUserDetails.setFirstname(requestUserDetails.getFirstname());
		origUserDetails.setLastname(requestUserDetails.getLastname());
		userDetailsRepository.save(origUserDetails);
		userRepository.updateUpdatedAtByUserLogin(login, Instant.now().toEpochMilli());
	}
}

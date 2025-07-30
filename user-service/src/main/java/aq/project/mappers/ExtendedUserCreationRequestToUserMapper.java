package aq.project.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.dto.ExtendedUserCreationRequest;
import aq.project.entities.Authority;
import aq.project.entities.User;
import aq.project.entities.UserDetails;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.utils.AuthorityHolder;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ExtendedUserCreationRequestToUserMapper {

	@Value("${date-format}")
	private String dateFormat; 
	@Autowired
	private AuthorityHolder authorityHolder;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Mapping(target = "login", source = "login")
	@Mapping(target = "password", expression = "java(toEncodedPassword(extendedUserRegistrationRequest.getPassword()))")
	@Mapping(target = "authorities", expression = "java(getUserAuthorities(extendedUserRegistrationRequest))")
	@Mapping(target = "userDetails", expression = "java(getUserDetails(extendedUserRegistrationRequest))")
	public abstract User toUser(ExtendedUserCreationRequest extendedUserRegistrationRequest) throws AuthorityNotFoundException;
		
	@Named("toEncodedPassword")
	protected String toEncodedPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	@Named("getUserAuthorities")
	@ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.THROW_EXCEPTION)
	protected List<Authority> getUserAuthorities(ExtendedUserCreationRequest extendedUserRegistrationRequest) throws AuthorityNotFoundException {
		List<Authority> authorities = new ArrayList<>();
		for(String authorityName : extendedUserRegistrationRequest.getAuthorities()) {
			Authority authority = authorityHolder.getAuthority(authorityName);
			authorities.add(authority);
		}
		return authorities;
	}
	
	@Named("getUserDetails")
	protected UserDetails getUserDetails(ExtendedUserCreationRequest extendedUserRegistrationRequest) {
		return new UserDetails(extendedUserRegistrationRequest.getFirstname(), 
				extendedUserRegistrationRequest.getLastname(), 
				extendedUserRegistrationRequest.getEmail(), 
				LocalDate.parse(extendedUserRegistrationRequest.getBirthDate(), DateTimeFormatter.ofPattern(dateFormat)));
	}
}

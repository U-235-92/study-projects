package aq.project.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.dto.ExtendedUserRequest;
import aq.project.entities.User;
import aq.project.entities.Authority;
import aq.project.entities.UserDetails;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ExtendedUserRequestToUserMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${date-format}")
	private String dateFormat; 
	
	@Mapping(target = "login", source = "login")
	@Mapping(target = "notBanned", source = "notBanned")
	@Mapping(target = "password", expression = "java(toEncodedPassword(extendedUserRequest.getPassword()))")
	@Mapping(target = "authorities", expression = "java(getUserAuthorities(extendedUserRequest))")
	@Mapping(target = "userDetails", expression = "java(getUserDetails(extendedUserRequest))")
	public abstract User toUser(ExtendedUserRequest extendedUserRequest);
	
	@Named("toEncodedPassword")
	public String toEncodedPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	@Named("getUserAuthorities")
	public List<Authority> getUserAuthorities(ExtendedUserRequest extendedUserRequest) {
		return extendedUserRequest.getAuthorities()
				.stream()
				.map(Authority::new)
				.toList();
	}
	
	@Named("getUserDetails")
	public UserDetails getUserDetails(ExtendedUserRequest extendedUserRequest) {
		return new UserDetails(extendedUserRequest.getFirstname(), 
				extendedUserRequest.getLastname(), 
				extendedUserRequest.getEmail(), 
				LocalDate.parse(extendedUserRequest.getBirthDate(), DateTimeFormatter.ofPattern(dateFormat)));
	}
}

package aq.project.mappers;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.dto.BasicUserRequest;
import aq.project.entities.User;
import aq.project.entities.UserDetails;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class BasicUserRequestToUserMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Mapping(target = "login", source = "login")
	@Mapping(target = "password", expression = "java(toEncodedPassword(basicUserRequest.getPassword()))")
	@Mapping(target = "userDetails", expression = "java(toUserDetails("
			+ "basicUserRequest.getFirstname(), "
			+ "basicUserRequest.getLastname(), "
			+ "basicUserRequest.getEmail(), "
			+ "basicUserRequest.getBirthDate()))")
	public abstract User toUser(BasicUserRequest basicUserRequest);
	
	@Named("toEncodedPassword")
	public String toEncodedPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	@Named("toUserDetails")
	public UserDetails toUserDetails(String firstname, String lastname, String email, LocalDate birthDate) {
		return new UserDetails(firstname, lastname, email, birthDate);
	}
}

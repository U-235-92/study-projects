package aq.project.mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import aq.project.dto.ExtendedUserResponse;
import aq.project.entities.User;
import aq.project.entities.Authority;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserToExtendedUserResponseMapper {

	@Value("date-format")
	private String dateFormat;
	@Value("date-time-format")
	private String dateTimeFormat;
	
	@Mapping(target = "login", source = "login")
	@Mapping(target = "firstname", expression = "java(getFirstname(user))")
	@Mapping(target = "lastname", expression = "java(getLastname(user))")
	@Mapping(target = "email", expression = "java(getEmail(user))")
	@Mapping(target = "birthDate", expression = "java(getBirthDate(user))")
	@Mapping(target = "createdAt", expression = "java(getCreatedAt(user))")
	@Mapping(target = "updatedAt", expression = "java(getUpdatedAt(user))")
	@Mapping(target = "authorities", expression = "java(getAuthorities(user))")
	@Mapping(target = "notBanned", source = "notBanned")
	public abstract ExtendedUserResponse toExtendedUserResponse(User user);
	
	public abstract List<ExtendedUserResponse> toExtendedUserResponseList(List<User> users);
	
	@Named("getFirstname")
	public String getFirstname(User user) {
		return user.getUserDetails().getFirstname();
	}
	
	@Named("getLastname")
	public String getLastname(User user) {
		return user.getUserDetails().getLastname();
	}
	
	@Named("getEmail")
	public String getEmail(User user) {
		return user.getUserDetails().getEmail();
	}
	
	@Named("getBirthDate")
	public String getBirthDate(User user) {
		return user.getUserDetails().getBirthDate().format(DateTimeFormatter.ofPattern(dateFormat));
	}
	
	@Named("getCreatedAt")
	public String getCreatedAt(User user) {
		Instant instant = Instant.ofEpochMilli(user.getCreatedAt());
		return LocalDateTime.from(instant).format(DateTimeFormatter.ofPattern(dateTimeFormat));
	}
	
	@Named("getUpdatedAt")
	public String getUpdatedAt(User user) {
		Instant instant = Instant.ofEpochMilli(user.getUpdatedAt());
		return LocalDateTime.from(instant).format(DateTimeFormatter.ofPattern(dateTimeFormat));
	}
	
	@Named("getAuthorities")
	public List<String> getAuthorities(User user) {
		return user.getAuthorities()
				.stream()
				.map(Authority::getName)
				.toList();
	}
}

package aq.project.mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import aq.project.dto.ExtendedUserResponse;
import aq.project.entities.User;
import aq.project.entities.UserAuthority;

import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserToExtendedUserResponseMapper {

	@Mapping(target = "login", source = "login")
	@Mapping(target = "firstname", expression = "java(getFirstname(user))")
	@Mapping(target = "lastname", expression = "java(getLastname(user))")
	@Mapping(target = "email", expression = "java(getEmail(user))")
	@Mapping(target = "birthDate", expression = "java(getBirthDate(user))")
	@Mapping(target = "createdAt", expression = "java(getCreatedAt(user))")
	@Mapping(target = "updatedAt", expression = "java(getUpdatedAt(user))")
	@Mapping(target = "authorities", expression = "java(getAuthorities(user))")
	@Mapping(target = "notBanned", source = "notBanned")
	ExtendedUserResponse toExtendedUserResponse(User user);
	
	List<ExtendedUserResponse> toExtendedUserResponseList(List<User> users);
	
	@Named("getFirstname")
	default String getFirstname(User user) {
		return user.getUserDetails().getFirstanme();
	}
	
	@Named("getLastname")
	default String getLastname(User user) {
		return user.getUserDetails().getLastname();
	}
	
	@Named("getEmail")
	default String getEmail(User user) {
		return user.getUserDetails().getEmail();
	}
	
	@Named("getBirthDate")
	default LocalDate getBirthDate(User user) {
		return user.getUserDetails().getBirthDate();
	}
	
	@Named("getCreatedAt")
	default LocalDateTime getCreatedAt(User user) {
		Instant instant = Instant.ofEpochMilli(user.getCreatedAt());
		return LocalDateTime.from(instant);
	}
	
	@Named("getUpdatedAt")
	default LocalDateTime getUpdatedAt(User user) {
		Instant instant = Instant.ofEpochMilli(user.getUpdatedAt());
		return LocalDateTime.from(instant);
	}
	
	@Named("getAuthorities")
	default List<String> getAuthorities(User user) {
		return user.getAuthorities()
				.stream()
				.map(UserAuthority::getName)
				.toList();
	}
}

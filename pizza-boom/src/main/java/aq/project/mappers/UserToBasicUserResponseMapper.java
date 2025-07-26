package aq.project.mappers;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import aq.project.dto.BasicUserResponse;
import aq.project.entities.User;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserToBasicUserResponseMapper {

	@Mapping(target = "login", source = "login")
	@Mapping(target = "firstname", expression = "java(getFirstname(user))")
	@Mapping(target = "lastname", expression = "java(getLastname(user))")
	@Mapping(target = "email", expression = "java(getEmail(user))")
	@Mapping(target = "birthDate", expression = "java(getBirthDate(user))")
	BasicUserResponse toBasicUserResponse(User user);
	
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
}

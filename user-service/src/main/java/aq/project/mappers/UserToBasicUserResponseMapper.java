package aq.project.mappers;

import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import aq.project.dto.BasicUserResponse;
import aq.project.entities.User;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserToBasicUserResponseMapper {


	@Value("${date-format}")
	private String dateFormat;
	
	@Mapping(target = "login", source = "login")
	@Mapping(target = "firstname", expression = "java(getFirstname(user))")
	@Mapping(target = "lastname", expression = "java(getLastname(user))")
	@Mapping(target = "email", expression = "java(getEmail(user))")
	@Mapping(target = "birthDate", expression = "java(getBirthDate(user))")
	public abstract BasicUserResponse toBasicUserResponse(User user);
	
	@Named("getFirstname")
	protected String getFirstname(User user) {
		return user.getUserDetails().getFirstname();
	}
	
	@Named("getLastname")
	protected String getLastname(User user) {
		return user.getUserDetails().getLastname();
	}
	
	@Named("getEmail")
	protected String getEmail(User user) {
		return user.getUserDetails().getEmail();
	}
	
	@Named("getBirthDate")
	protected String getBirthDate(User user) {
		return user.getUserDetails().getBirthDate().format(DateTimeFormatter.ofPattern(dateFormat));
	}
}

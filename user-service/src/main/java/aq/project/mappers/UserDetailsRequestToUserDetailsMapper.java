package aq.project.mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import aq.project.dto.UserDetailsRequest;
import aq.project.entities.UserDetails;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserDetailsRequestToUserDetailsMapper {

	@Value("${date-format}")
	private String dateFormat;
	
	@Mapping(target = "firstname", source = "firstname")
	@Mapping(target = "lastname", source = "lastname")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "birthDate", expression = "java(toDate(userDetailsRequest))")
	public abstract UserDetails toUserDetails(UserDetailsRequest userDetailsRequest);
	
	@Named("toDate")
	public LocalDate toDate(UserDetailsRequest userDetailsRequest) {
		return LocalDate.parse(userDetailsRequest.getBirthDate(), DateTimeFormatter.ofPattern(dateFormat));
	}
}

package aq.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import aq.project.dto.user.UserRequest;
import aq.project.dto.user.UserResponse;
import aq.project.entities.Role;
import aq.project.entities.User;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserMapper {

	@Mapping(target = "role", expression = "java(toRole(userRequest))")
	public abstract User toUser(UserRequest userRequest);
	
	protected Role toRole(UserRequest userRequest) {
		return Role.valueOf(userRequest.getRole());
	}
	
	@Mapping(target = "role", expression = "java(toRole(user))")
	public abstract UserResponse toUserResponse(User user);
	
	protected String toRole(User user) {
		return user.getRole().name();
	}
}

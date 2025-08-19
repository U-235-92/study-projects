package aq.project.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import aq.project.dtos.UserRequest;
import aq.project.dtos.UserResponse;
import aq.project.entities.User;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserMapper {

	User toUser(UserRequest userRequest);
	
	UserResponse toUserResponse(User user);
	
	List<UserResponse> toUserResponses(List<User> users);
}

package aq.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import aq.project.dto.AuthorityRequest;
import aq.project.entities.Authority;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class AuthorityRequestToAuthorityMapper {

	@Mapping(source = "authority", target = "name")
	public abstract Authority toAuthority(AuthorityRequest authorityRequest);
}

package aq.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import aq.project.dto.AccountResponse;
import aq.project.dto.AccountRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class AccountMapper {

	@Mapping(source = "login", target = "login")
	@Mapping(source = "password", target = "password")
	@Mapping(target = "role", expression = "java(toRole(accountRequest.getRole()))")
	public abstract Account toAccount(AccountRequest accountRequest);
	
	@Named("toRole")
	protected Role toRole(String role) {
		return Role.valueOf(role.toUpperCase());
	}
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "login", target = "login")
	@Mapping(target = "role", expression = "java(toStringRole(account.getRole()))")
	@Mapping(target = "isNotBlocked", expression = "java(isNotBlocked(account))")
	public abstract AccountResponse toAccountResponse(Account account);
	
	@Named("toStringRole")
	protected String toStringRole(Role role) {
		return role.name().toUpperCase();
	}
	
	@Named("isNotBlocked")
	protected boolean isNotBlocked(Account account) {
		return account.isNotBlocked();
	}
}

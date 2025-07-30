package aq.project.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aq.project.dto.UserAuthoritiesUpdateRequest;
import aq.project.entities.Authority;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.utils.AuthorityHolder;

@Component
public class UserAuthorityRequestToAuthorityMapper {

	@Autowired
	private AuthorityHolder authorityHolder;
	
	public List<Authority> toAuthorities(UserAuthoritiesUpdateRequest userAuthorityRequest) throws AuthorityNotFoundException {
		List<Authority> authorities = new ArrayList<>();
		for(String authorityName : userAuthorityRequest.getAuthorities()) {
			Authority authority = authorityHolder.getAuthority(authorityName);
			authorities.add(authority);
		}
		return authorities;
	}
}

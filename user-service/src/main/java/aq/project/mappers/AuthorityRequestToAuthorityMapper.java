package aq.project.mappers;

import org.springframework.stereotype.Component;

import aq.project.dto.AuthorityRequest;
import aq.project.entities.Authority;
import aq.project.exceptions.AuthorityNotFoundException;
import aq.project.utils.AuthorityHolder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityRequestToAuthorityMapper {

	private final AuthorityHolder authorityHolder;
	
	public Authority toAuthority(AuthorityRequest authorityRequest) throws AuthorityNotFoundException {
		return authorityHolder.getAuthority(authorityRequest.getAuthority());
	}
}

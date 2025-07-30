package aq.project.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import aq.project.dto.AuthorityResponse;
import aq.project.entities.Authority;

@Component
public class AuthorityToAuthorityResponseMapper {

	public List<AuthorityResponse> toAuthorityResponse(List<Authority> authorities) {
		return authorities.stream()
				.map(authority -> new AuthorityResponse(authority.getId(), authority.getName()))
				.toList();
	}
}

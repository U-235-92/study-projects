package aq.project.controllers;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.AuthorityRequest;
import aq.project.dto.AuthorityResponse;
import aq.project.services.AuthorityService;
import lombok.RequiredArgsConstructor;

@Transactional
@RestController
@RequiredArgsConstructor
public class AuthorityController {

	private final AuthorityService authorityService;
	
	@PostMapping("${authority.create}")
	public String createAuthority(@RequestBody AuthorityRequest authorityRequest) {
		authorityService.createAuthority(authorityRequest);
		return String.format("Authority [ %s ] was created successfully", authorityRequest.getAuthority());
	}
	
	@PatchMapping("${authority.update}/{name}")
	public String updateAuthority(@PathVariable(required = true) String name, @RequestBody AuthorityRequest authorityRequest) {
		authorityService.updateAuthority(name, authorityRequest);
		return String.format("Authority with name [ %s ] was updated successfully", name);
	}
	
	@DeleteMapping("${authority.delete}/{name}")
	public String deleteAuthority(@PathVariable(required = true) String name) {
		authorityService.deleteAuthority(name);
		return String.format("Authority with id [ %s ] was deleted successfully", name);
	}
	
	@GetMapping("${authority.all-authorities}")
	public List<AuthorityResponse> findAllAuthorities() {
		return authorityService.findAllAuthorities();
	}
}

package aq.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.UserLoginRequestDto;
import aq.project.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	
	@GetMapping("/generate-token")
	public String generateToken(@RequestBody(required = true) UserLoginRequestDto userLoginRequest) {
		return authenticationService.generateToken(userLoginRequest.username(), userLoginRequest.password());
	}
}

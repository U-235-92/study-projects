package aq.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.AuthenticationRequest;
import aq.project.services.AccessTokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AccessTokenController {

	private final AccessTokenService accessTokenService;
	
	@GetMapping("/generate-access-token")
	public ResponseEntity<String> generateAccessToken(@RequestBody(required = true) AuthenticationRequest authenticationRequest) {
		String accessToken = accessTokenService.generateAccessToken(authenticationRequest);
		return new ResponseEntity<String>(accessToken.trim(), HttpStatus.OK);
	}
	
	@PatchMapping("/revoke-access-token/{login}")
	public ResponseEntity<String> revokeAccessToken(@PathVariable(required =  true) String login) {
		accessTokenService.revokeAccessToken(login);
		String msg = String.format("Access token of account with login [ %s ] was revoked", login);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@GetMapping("/check-valid-access-token/{login}")
	public ResponseEntity<String> checkValidAccessToken(@PathVariable(required = true) String login, 
			@RequestHeader(required = true, name = "Authorization") String accessToken) {
		String msg = accessTokenService.isValidAccessToken(login, accessToken.substring(7)) ? 
																	"Access token is valid" : "Access token is invalid";
		return new ResponseEntity<String>(msg, HttpStatus.OK);	
	}
}

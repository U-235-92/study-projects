package aq.project.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.AccessTokenNotFoundException;
import aq.project.exceptions.AuthenticationRequestException;
import aq.project.exceptions.NoBearerTokenException;

@RestControllerAdvice
public class AccessTokenControllerAdvice {

	@ExceptionHandler(exception = AccessTokenNotFoundException.class)
	public ResponseEntity<String> handleAccessTokenNotFoundException(AccessTokenNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	} 
	
	@ExceptionHandler(exception = AuthenticationRequestException.class)
	public ResponseEntity<String> handleAuthenticationRequestException(AuthenticationRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	} 
	
	@ExceptionHandler(exception = NoBearerTokenException.class)
	public ResponseEntity<String> handleNoBearerTokenException(NoBearerTokenException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
}

package aq.project.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.AuthorityAlreadyExistException;
import aq.project.exceptions.AuthorityNotFoundException;

@RestControllerAdvice
public class AuthorityControllerAdvice {
	
	@ExceptionHandler(value = AuthorityAlreadyExistException.class)
	public ResponseEntity<String> handleAuthorityAlreadyExistException(AuthorityAlreadyExistException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = AuthorityNotFoundException.class)
	public ResponseEntity<String> handleAuthorityNotFoundException(AuthorityNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND); 	
	}
}

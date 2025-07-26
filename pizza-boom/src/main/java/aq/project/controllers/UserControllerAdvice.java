package aq.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.NoValidUserException;
import aq.project.exceptions.UserLoginAlreadyExistsException;
import aq.project.exceptions.UserLoginNotFoundException;

@RestControllerAdvice
public class UserControllerAdvice {
	
	@ExceptionHandler(value = UserLoginNotFoundException.class)
	public ResponseEntity<String> handleUserLoginNotFoundException(UserLoginNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(value = UserLoginAlreadyExistsException.class)
	public ResponseEntity<String> handleUserLoginExistsException(UserLoginAlreadyExistsException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NoValidUserException.class)
	public ResponseEntity<String> handleIncorrectUserDataException(NoValidUserException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

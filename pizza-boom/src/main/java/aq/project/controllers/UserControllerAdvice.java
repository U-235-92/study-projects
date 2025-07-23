package aq.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.UserIdNotFoundException;
import aq.project.exceptions.UserLoginNotFoundException;

@RestControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(value = UserIdNotFoundException.class)
	public ResponseEntity<String> handleUserIdNotFoundException(UserIdNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(value = UserLoginNotFoundException.class)
	public ResponseEntity<String> handleUserLoginNotFoundException(UserLoginNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND); 
	}
}

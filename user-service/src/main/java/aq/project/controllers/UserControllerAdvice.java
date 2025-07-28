package aq.project.controllers;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.EmptyDtoException;
import aq.project.exceptions.InvalidBasicUserRequestException;
import aq.project.exceptions.InvalidBasicUserResponseException;
import aq.project.exceptions.InvalidExtendedUserRequestException;
import aq.project.exceptions.InvalidExtendedUserResponseException;
import aq.project.exceptions.InvalidStringPropertyException;
import aq.project.exceptions.InvalidUserDetailsRequestException;
import aq.project.exceptions.UnknownPropertyException;
import aq.project.exceptions.UserLoginAlreadyExistException;
import aq.project.exceptions.UserLoginNotFoundException;

@RestControllerAdvice
public class UserControllerAdvice {
	
	@ExceptionHandler(value = UserLoginNotFoundException.class)
	public ResponseEntity<String> handleUserLoginNotFoundException(UserLoginNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(value = UserLoginAlreadyExistException.class)
	public ResponseEntity<String> handleUserLoginExistsException(UserLoginAlreadyExistException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidBasicUserRequestException.class)
	public ResponseEntity<String> handleInvalidBasicUserRequestException(InvalidBasicUserRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidBasicUserResponseException.class)
	public ResponseEntity<String> handleInvalidBasicUserResponseException(InvalidBasicUserResponseException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = InvalidExtendedUserRequestException.class)
	public ResponseEntity<String> handleInvalidExtendedUserRequestException(InvalidExtendedUserRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidExtendedUserResponseException.class)
	public ResponseEntity<String> handleInvalidExtendedUserResponseException(InvalidExtendedUserResponseException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = InvalidUserDetailsRequestException.class)
	public ResponseEntity<String> handleInvalidUserDetailsRequestException(InvalidUserDetailsRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = EmptyDtoException.class)
	public ResponseEntity<String> handleEmptyDtoException(EmptyDtoException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UnknownPropertyException.class)
	public ResponseEntity<String> handleUnknownPropertyException(UnknownPropertyException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<String> handleUnknownPropertyException(AccessDeniedException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidStringPropertyException.class)
	public ResponseEntity<String> handleInvalidStringPropertyException(InvalidStringPropertyException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	} 
}

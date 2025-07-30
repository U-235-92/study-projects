package aq.project.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.AccessDeniedException;
import aq.project.exceptions.EmailAlreadyExistException;
import aq.project.exceptions.InvalidPropertyException;
import aq.project.exceptions.LoginAlreadyExistException;
import aq.project.exceptions.LoginNotFoundException;
import aq.project.exceptions.NullDtoException;

@RestControllerAdvice
public class CommonControllerAdvice {

	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = EmailAlreadyExistException.class)
	public ResponseEntity<String> handleEmailAlreadyExistException(EmailAlreadyExistException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidPropertyException.class)
	public ResponseEntity<String> handleInvalidPropertiesException(InvalidPropertyException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = LoginAlreadyExistException.class)
	public ResponseEntity<String> handleLoginAlreadyExistException(LoginAlreadyExistException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = LoginNotFoundException.class)
	public ResponseEntity<String> handleLoginNotFoundException(LoginNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = NullDtoException.class)
	public ResponseEntity<String> handleNullDtoException(NullDtoException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
}

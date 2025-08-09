package aq.project.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.AccountAlreadyExistException;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AccountResponseException;
import aq.project.exceptions.CreateAccountRequestException;
import aq.project.exceptions.EditAccountRequestException;

@RestControllerAdvice
public class AccountControllerAdvice {

	@ExceptionHandler(exception = UsernameNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception = AccountNotFoundException.class)
	public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception = AccountAlreadyExistException.class)
	public ResponseEntity<String> handleAccountAlreadyExistException(AccountAlreadyExistException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = AccountResponseException.class)
	public ResponseEntity<String> handleAccountResponseException(AccountResponseException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(exception = CreateAccountRequestException.class)
	public ResponseEntity<String> handleAccountRequestException(CreateAccountRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = EditAccountRequestException.class)
	public ResponseEntity<String> handleEditAccountRequestException(EditAccountRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	} 
}

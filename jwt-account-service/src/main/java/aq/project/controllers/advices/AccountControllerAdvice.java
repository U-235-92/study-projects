package aq.project.controllers.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.AccountAlreadyExistException;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AccountRequestException;
import aq.project.exceptions.AccountResponseException;
import aq.project.exceptions.BlockedAccountException;
import aq.project.exceptions.EditAccountRequestException;

@RestControllerAdvice
public class AccountControllerAdvice {
	
	@ExceptionHandler(exception = UsernameNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(exception = BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
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
	
	@ExceptionHandler(exception = AccountRequestException.class)
	public ResponseEntity<String> handleAccountRequestException(AccountRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = EditAccountRequestException.class)
	public ResponseEntity<String> handleEditAccountRequestException(EditAccountRequestException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(exception = BlockedAccountException.class)
	public ResponseEntity<String> handleBlockedAccountException(BlockedAccountException exc) {
		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.FORBIDDEN);
	}
}

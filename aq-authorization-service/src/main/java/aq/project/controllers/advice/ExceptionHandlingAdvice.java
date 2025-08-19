package aq.project.controllers.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exceptions.ClientIdNotFoundException;
import aq.project.exceptions.ClientNameNotFoundException;
import aq.project.exceptions.UserAlreadyExistException;
import aq.project.exceptions.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
public class ExceptionHandlingAdvice {

	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = UserAlreadyExistException.class)
	public String onUserAlreadyExistsException(UserAlreadyExistException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = UserNotFoundException.class)
	public String onUserNotFoundException(UserNotFoundException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = ClientIdNotFoundException.class)
	public String onClientIdNotFoundException(ClientIdNotFoundException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = ClientNameNotFoundException.class)
	public String onClientNameNotFoundException(ClientNameNotFoundException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = ConstraintViolationException.class)
	public List<Violation> onConstraintViolationException(ConstraintViolationException exc) {
		return exc.getConstraintViolations()
				.stream()
				.map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
				.toList();
	}
	
	@Getter
	@AllArgsConstructor
	private class Violation {
		private String key;
		private String value;
	}
}

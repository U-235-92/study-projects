package aq.project.controller.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import aq.project.exception.DeleteMessageViolationException;
import aq.project.exception.EditMessageViolationException;
import aq.project.exception.MessageNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestControllerAdvice
public class ExceptionHandleAdvice {

	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = MessageNotFoundException.class)
	public String onMessageNotFoundException(MessageNotFoundException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = EditMessageViolationException.class)
	public String onEditMessageViolationException(EditMessageViolationException exc) {
		return exc.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = DeleteMessageViolationException.class)
	public String onDeleteMessageViolationException(DeleteMessageViolationException exc) {
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

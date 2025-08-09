package aq.project.exceptions;

import java.util.Set;

import aq.project.dto.EditAccountRequest;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditAccountRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation<EditAccountRequest>> violations;
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Bad account request:\n");
		violations.forEach(v -> {
			sb.append(v.getPropertyPath());
			sb.append(": ");
			sb.append(v.getInvalidValue());
			sb.append("\n");
		});
		return sb.toString();
	}
}

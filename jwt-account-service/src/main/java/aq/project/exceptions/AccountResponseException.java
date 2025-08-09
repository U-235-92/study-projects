package aq.project.exceptions;

import java.util.Set;

import aq.project.dto.AccountResponse;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountResponseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Set<ConstraintViolation<AccountResponse>> violations;
	
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Bad account response:\n");
		violations.forEach(v -> {
			sb.append(v.getPropertyPath());
			sb.append(": ");
			sb.append(v.getInvalidValue());
			sb.append("\n");
		});
		return sb.toString();
	}
}

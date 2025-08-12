package aq.project.exceptions;

import java.util.Set;

import aq.project.dto.AccountRequest;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Set<ConstraintViolation<AccountRequest>> violations;
	
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

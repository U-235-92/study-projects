package aq.project.mappers;

import aq.project.models.InvalidProperty;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ViolationToInvalidPropertyMapper {

	public static InvalidProperty toInvalidProperty(ConstraintViolation<?> violation) {
		return new InvalidProperty(
				violation.getPropertyPath().toString(), 
				violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString());
	}
 }

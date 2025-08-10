package aq.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AccountResponse {

	@Positive
	private int id;
	@NotBlank
	private String login;
	@NotBlank @Pattern(regexp = "ADMIN|EDITOR|READER")
	private String role;
	private boolean isNotBlocked;
}

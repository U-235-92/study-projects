package aq.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CreateAccountRequest {

	@NotBlank
	private String login;
	@NotBlank
	private String password;
	@NotBlank @Pattern(regexp = "ADMIN|EDITOR|READER")
	private String role;
}

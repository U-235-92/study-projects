package aq.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AuthenticationRequest {

	@NotBlank
	private String login;
	@NotBlank
	private String password;
}

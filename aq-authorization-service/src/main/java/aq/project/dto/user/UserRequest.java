package aq.project.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserRequest {

	@NotBlank
	private String login;
	@NotBlank
	private String password;
	@Email
	private String email;
	@NotEmpty
	private String role;
}

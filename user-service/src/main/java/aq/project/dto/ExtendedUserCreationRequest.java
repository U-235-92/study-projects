package aq.project.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ExtendedUserCreationRequest {

	@NotBlank @Size(max = 255)
	private String login;
	@NotBlank @Size(max = 255)
	private String password;
	@NotBlank @Size(max = 255)
	private String firstname;
	@NotBlank @Size(max = 255)
	private String lastname;
	@Email @NotBlank @Size(max = 255)
	private String email;
	@NotBlank @Pattern(regexp = "(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2])-\\d{4}")
	private String birthDate;
	@NotNull
	private List<String> authorities;
}

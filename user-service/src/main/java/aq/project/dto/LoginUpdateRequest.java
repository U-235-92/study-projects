package aq.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class LoginUpdateRequest {

	@NotBlank @Size(max = 255)
	private String oldLogin;
	@NotBlank @Size(max = 255)
	private String newLogin;
}

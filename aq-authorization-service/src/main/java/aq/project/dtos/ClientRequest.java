package aq.project.dtos;

import java.util.List;

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
public class ClientRequest {
	
	@NotBlank
	private String name;
	@NotBlank
	private String secret;
	@NotEmpty
	private List<String> scopes;
	@NotBlank
	private String authenticationMethod;
	@NotBlank
	private String authorizationGrantType;
}

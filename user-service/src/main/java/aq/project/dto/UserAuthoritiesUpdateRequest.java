package aq.project.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class UserAuthoritiesUpdateRequest {

	@NotNull
	private List<String> authorities;
}

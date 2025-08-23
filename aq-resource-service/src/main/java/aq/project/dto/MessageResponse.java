package aq.project.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MessageResponse {

	private int id;
	@NotBlank
	private String author;
	@NotBlank
	private String postedAt;
	@NotBlank
	private String updatedAt;
	@NotNull @Length(min = 0, max = 1024)
	private String text;
}

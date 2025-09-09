package aq.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureRequestDto {

	@Positive
	private long id;
	@Positive
	private int count;
	@NotEmpty
	private String color;
	@NotEmpty
	private String product;
	@Positive
	private long createdAt;
}

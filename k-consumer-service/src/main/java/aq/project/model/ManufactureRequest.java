package aq.project.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureRequest {

	@Positive
	private long id;
	@Positive
	private int count;
	@NotEmpty
	private String color;
	@NotEmpty
	private String product;
	@NotNull
	private LocalDateTime createdAt;
}

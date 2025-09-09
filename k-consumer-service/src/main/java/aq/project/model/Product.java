package aq.project.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@NotEmpty
	private String id;
	@NotEmpty
	private String party;
	@NotEmpty
	private String color;
	@NotEmpty
	private String name;
	@NotNull
	private LocalDateTime createdAt;
}

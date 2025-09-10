package aq.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufactureRequestDto {

	private long id;
	private int count;
	private String color;
	private String product;
	private long createdAt;
}

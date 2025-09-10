package aq.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String id;
	private String name;
	private String party;
	private String color;
	private long createdAt;
}

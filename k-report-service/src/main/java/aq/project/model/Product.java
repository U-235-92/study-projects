package aq.project.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private String id;
	private String name;
	private String party;
	private String color;
	private LocalDateTime createdAt;
}

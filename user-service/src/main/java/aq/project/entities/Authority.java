package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Access(AccessType.PROPERTY)
@Table(name = "authorities")
public class Authority {

	private int id;
	@NotBlank @Size(max = 255)
	private String name;
	
	public Authority(String name) {
		this.name = name;
	}
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	
	@Column(name = "name", unique = true)
	public String getName() {
		return name;
	}
}

package aq.project.entities;

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
@Table(name = "user_authorities")
public class UserAuthority {

	@Id @GeneratedValue
	private int id;
	@NotBlank
	@Size(max = 255)
	@Column(name = "name", unique = true)
	private String name;
	
	public UserAuthority(String name) {
		this.name = name;
	}
}

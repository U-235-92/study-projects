package aq.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_authorities")
public class UserAuthority {

	@Id @GeneratedValue
	private int id;
	@Column(name = "name", unique = true)
	private String name;
	
	public UserAuthority(String name) {
		this.name = name;
	}
}

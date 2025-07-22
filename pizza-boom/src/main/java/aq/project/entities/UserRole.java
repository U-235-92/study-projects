package aq.project.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole {

	@Id @GeneratedValue
	private int id;
	private String name;
	@OneToMany
	private List<UserAuthority> userAuthorities;
}

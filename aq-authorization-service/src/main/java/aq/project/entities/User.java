package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@Access(AccessType.PROPERTY)
public class User {

	@Positive
	private int id;
	@NotBlank
	private String login;
	@NotBlank
	private String password;
	@Email
	private String email;
	@NotNull
	private Role role;

	public User(String login, String password, String email, Role role) {
		super();
		this.login = login;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(unique = true)
	public String getLogin() {
		return login;
	}
	
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}

}

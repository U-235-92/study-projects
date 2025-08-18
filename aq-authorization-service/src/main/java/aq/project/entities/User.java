package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

	public User(@NotBlank String login, @NotBlank String password, @Email String email) {
		super();
		this.login = login;
		this.password = password;
		this.email = email;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}

	@Column(unique = true)
	public String getLogin() {
		return login;
	}
	
	public void setPassword(String password) {
		if(this.password == null)
			this.password = password;
	}

	@Column(unique = true)
	public String getEmail() {
		return email;
	}
}

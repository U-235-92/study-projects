package aq.project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id @GeneratedValue
	private int id;
	private String login;
	private String password;
	@OneToOne
	private UserRole userRole;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;
	
	public User(String login, String password, UserDetails userDetails, UserRole userRole) {
		this.login = login;
		this.password = password;
		this.userDetails = userDetails;
		this.userRole = userRole;
	}
}

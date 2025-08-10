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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "accounts")
@Access(AccessType.PROPERTY)
public class Account {

	private int id;
	private Role role;
	private String login;
	private String password;
	private boolean isNotBlocked;
	
	public Account(String login, String password, boolean isNotBlocked, Role role) {
		this.login = login;
		this.password = password;
		this.isNotBlocked = isNotBlocked;
		this.role = role;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "is_not_blocked")
	public boolean isNotBlocked() {
		return isNotBlocked;
	}
	
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}
}

package aq.project.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id @GeneratedValue
	private int id;
	@Column(name = "login", unique = true)
	private String login;
	private String password;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities", 
					joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "authority_id"),
			uniqueConstraints = @UniqueConstraint(name = "uq_user_authority", columnNames = {"user_id", "authority_id"}))
	private List<UserAuthority> authorities;
	
	public User(String login, String password, UserDetails userDetails, List<UserAuthority> authorities) {
		this.login = login;
		this.password = password;
		this.userDetails = userDetails;
		this.authorities = authorities;
	}
}

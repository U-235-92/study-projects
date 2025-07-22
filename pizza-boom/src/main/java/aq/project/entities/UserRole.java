package aq.project.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities", 
					joinColumns = @JoinColumn(name = "user_id"), 
					inverseJoinColumns = @JoinColumn(name = "authority_id"),
					uniqueConstraints = @UniqueConstraint(name = "uq_user_authority", columnNames = {"user_id", "authority_id"}))
	private List<UserAuthority> userAuthorities;
	
	public UserRole(String name, List<UserAuthority> userAuthorities) {
		this.name = name;
		this.userAuthorities = userAuthorities;
	}
}

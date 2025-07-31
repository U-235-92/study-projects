package aq.project.entities;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
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
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
@Access(AccessType.PROPERTY)
public class User {

	@Transient
	private static final long NO_ADJUSTED_VALUE = -1L;
	
	private int id;
	@NotBlank @Size(max = 255)
	private String login;
	@NotBlank @Size(max = 255)
	private String password;
	@NotNull
	private UserDetails userDetails;
	@NotNull
	private List<Authority> authorities;
	private boolean notBanned = true;
	private long createdAt = NO_ADJUSTED_VALUE;
	private long updatedAt = NO_ADJUSTED_VALUE;
	
	public User(String login, String password, boolean notBanned, UserDetails userDetails, List<Authority> authorities) {
		super();
		this.login = login;
		this.password = password;
		this.notBanned = notBanned;
		this.userDetails = userDetails;
		this.authorities = authorities;
		this.createdAt = Instant.now().toEpochMilli();
		this.updatedAt = Instant.ofEpochMilli(createdAt).toEpochMilli();
	}
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}
	
	@Column(unique = true, updatable = true)
	public String getLogin() {
		return login;
	}
	
	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	@JoinColumn(name = "user_details_id")
	public UserDetails getUserDetails() {
		return userDetails;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities",
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "authority_id"),
			uniqueConstraints = @UniqueConstraint(name = "uq_user_authority", columnNames = {"user_id", "authority_id"}))
	public List<Authority> getAuthorities() {
		return authorities;
	}
	
	public long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(long createdAt) {
		if(this.createdAt == NO_ADJUSTED_VALUE)
			this.createdAt = createdAt;
	}
}

package aq.project.entities;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class User {

	@Transient
	private static final long NO_ADJUSTED_CREATED_AT = -1L;
	
	@Id
	@NotBlank
	@Size(max = 255)
	@Column(unique = true)
	private String login;
	@NotBlank
	@Size(max = 255)
	private String password;
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;
	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authorities", 	
			joinColumns = @JoinColumn(name = "user_login"), 
			inverseJoinColumns = @JoinColumn(name = "authority_id"),
			uniqueConstraints = @UniqueConstraint(name = "uq_user_authority", columnNames = {"user_login", "authority_id"}))
	private List<UserAuthority> authorities;
	private long createdAt = NO_ADJUSTED_CREATED_AT;
	private long updatedAt;
	private boolean notBanned;
	
	public User(String login, String password, boolean notBanned, UserDetails userDetails, List<UserAuthority> authorities) {
		super();
		this.login = login;
		this.password = password;
		this.notBanned = notBanned;
		this.userDetails = userDetails;
		this.authorities = authorities;
		this.createdAt = Instant.now().toEpochMilli();
		this.updatedAt = Instant.ofEpochMilli(createdAt).toEpochMilli();
	}
	
	public void setCreatedAt(long createdAt) {
		if(this.createdAt == NO_ADJUSTED_CREATED_AT)
			this.createdAt = createdAt;
	}
	
	public long getCreatedAt() {
		return createdAt;
	}
}

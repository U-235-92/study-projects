package aq.project.entities;

import java.util.List;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "clients")
@Access(AccessType.PROPERTY)
public class Client {

	@Positive
	private int id;
	@NotBlank
	private String name;
	@NotBlank
	private String secret;
	@NotEmpty
	private List<Scope> scopes;
	
	private final String authenticationMethod = ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue();
	private final String authorizationGrantType = AuthorizationGrantType.CLIENT_CREDENTIALS.getValue();
	
	public Client(@NotBlank String name, @NotBlank String secret, @NotEmpty List<Scope> scopes) {
		super();
		this.name = name;
		this.secret = secret;
		this.scopes = scopes;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}
	
	@Column(unique = true)
	public String getName() {
		return name;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "scope_id"),
				uniqueConstraints = @UniqueConstraint(name = "uc_client_scope_id", columnNames = {"client_id", "scope_id"}))
	public List<Scope> getScopes() {
		return scopes;
	}
	
	public void setSecret(@NotBlank String secret) {
		if(this.secret == null)
			this.secret = secret;
	}
}

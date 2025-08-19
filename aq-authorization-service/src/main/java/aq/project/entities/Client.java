package aq.project.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "clients")
@Access(AccessType.PROPERTY)
public class Client {

	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String secret;
	@NotEmpty
	private List<String> scopes;
	@NotBlank
	private String authenticationMethod;
	@NotBlank
	private String authorizationGrantType;
	
	public Client(String name, String secret, List<String> scopes, String authenticationMethod, String authorizationGrantType) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.secret = secret;
		this.scopes = scopes;
		this.authenticationMethod = authenticationMethod;
		this.authorizationGrantType = authorizationGrantType;
	}

	@Id
	public String getId() {
		return id;
	}
	
	@Column(unique = true)
	public String getName() {
		return name;
	}

	@Column(name = "scope")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
	public List<String> getScopes() {
		return scopes;
	}
}

package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "scopes")
@Access(AccessType.PROPERTY)
public class Scope {

	@Positive
	private int id;
	@NotBlank
	private String scope;
	
	public Scope(@NotBlank String scope) {
		super();
		this.scope = scope;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}
	
	@Column(unique = true)
	public String getScope() {
		return scope;
	}
}

package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "access_tokens")
@Access(AccessType.PROPERTY)
public class AccessToken {

	private int id;
	private Account account;
	private String jwt;
	
	public AccessToken(Account account, String jwt) {
		this.account = account;
		this.jwt = jwt;
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	@OneToOne(optional = false)
	public Account getAccount() {
		return account;
	}
}

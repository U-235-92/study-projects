package aq.project.entities;

import java.time.LocalDate;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Access(AccessType.PROPERTY)
@Table(name = "user_details")
public class UserDetails {

	private int id;
	@NotBlank @Size(max = 255)
	private String firstname;
	@NotBlank @Size(max = 255)
	private String lastname;
	@Email @Size(max = 255)
	private String email;
	private LocalDate birthDate;
	
	public UserDetails(String firstname, String lastname, String email, LocalDate birthDate) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthDate = birthDate;
	}

	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	@Column(name = "email", unique = true)
	public String getEmail() {
		return email;
	}

	@Temporal(TemporalType.DATE)
	public LocalDate getBirthDate() {
		return birthDate;
	}
}

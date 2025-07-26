package aq.project.entities;

import java.time.LocalDate;

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
@Table(name = "user_details")
public class UserDetails {

	@Id @GeneratedValue
	private int id;
	@NotBlank
	@Size(max = 255)
	private String firstanme;
	@NotBlank
	@Size(max = 255)
	private String lastname;
	@Email
	@Column(name = "email", unique = true)
	private String email;
	@Temporal(TemporalType.DATE)
	private LocalDate birthDate;
	
	public UserDetails(String firstname, String lastname, String email, LocalDate birthDate) {
		this.firstanme = firstname;
		this.lastname = lastname;
		this.email = email;
		this.birthDate = birthDate;
	}
}

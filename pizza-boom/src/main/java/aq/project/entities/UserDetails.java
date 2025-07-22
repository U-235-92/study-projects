package aq.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_details")
public class UserDetails {

	@Id @GeneratedValue
	private int id;
	private String email;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
}

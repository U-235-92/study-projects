package aq.project.entities;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roles")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Access(AccessType.PROPERTY)
public enum Role {

	ADMIN(1), EDITOR(2), READER(3);
	
	private int id;

	@Id
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}

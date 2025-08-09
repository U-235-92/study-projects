package aq.project.entities;

import java.math.BigDecimal;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "items")
@Access(AccessType.PROPERTY)
public class Item {

	private int id;
	private String name;
	private BigDecimal price;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	@Column(name = "item_name")
	public String getName() {
		return name;
	}
	
	@Column(name = "item_price")
	public BigDecimal getPrice() {
		return price;
	}
}

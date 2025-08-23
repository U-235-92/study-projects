package aq.project.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "messages")
@Access(AccessType.PROPERTY)
public class Message {

	private int id;
	@NotBlank @Length(min = 1, max = 255)
	private String author;
	@PositiveOrZero
	private long postedAt;
	@PositiveOrZero
	private long updatedAt;
	@NotNull @Length(min = 0, max = 1024)
	private String text;
	
	public Message(String author, String text) {
		super();
		this.author = author;
		this.text = text;
		this.postedAt = System.currentTimeMillis();
		this.updatedAt = postedAt;
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	@Column(length = 1024)
	public String getText() {
		return text;
	}

	@Column(name = "posted_at", nullable = false)
	public long getPostedAt() {
		return postedAt;
	}

	@Column(name = "updated_at", nullable = false)
	public long getUpdatedAt() {
		return updatedAt;
	}
}

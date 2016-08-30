package com.prodyna.pac.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class to represent an option of a vote.
 * <p>
 * This class is annotated to work with MongoDB.
 */
@Document
public class Option {

	@Id
	private String id;
	private String name;
	private long counter;

	// necessary for automatic mapping
	public Option() {
		super();
	}

	public Option(String name, long counter) {
		this.name = name;
		this.counter = counter;
	}

	public Option(String id, String name, long counter) {
		this.id = id;
		this.name = name;
		this.counter = counter;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getCounter() {
		return counter;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", name=" + name + ", counter=" + counter + "]";
	}

}

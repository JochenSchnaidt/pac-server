package com.prodyna.pac.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.SelectionDTO;
import com.prodyna.pac.exception.ValidationException;

/**
 * Entity class to represent an user. Creation and update of entities only via {@code Builder}.
 * <p>
 * This class is annotated to work with MongoDB.
 */
@Document(collection = "users")
public class User {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean administrator;
	private Set<Selection> selections;
	@CreatedDate
	private Date creationDate;

	// necessary for automatic mapping
	public User() {
		super();
	}

	/**
	 * Private constructor to prevent instantiation of entity without {@code Builder}
	 *
	 * @param builder
	 *            preinitialized {@code Builder}
	 */
	private User(Builder builder) {

		if (null == builder) {
			throw new ValidationException("Validation in User for parameter builder failed");
		}

		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.password = builder.password;
		this.administrator = builder.administrator;
		this.selections = builder.selections;
		this.creationDate = builder.creationDate;
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public Set<Selection> getSelections() {
		return selections;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=***" + ", administrator=" + administrator + ", selections=" + selections
		        + ", creationDate=" + creationDate + "]";
	}

	/**
	 * Builder for {@code User}
	 */
	public static class Builder {

		private String id;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private boolean administrator;
		private Set<Selection> selections;
		private Date creationDate;

		/**
		 * Instantiate raw {@code Builder} instance
		 */
		public Builder() {
			creationDate(null);
		}

		/**
		 * Sets the {@code User.firstName} property
		 *
		 * @param firstName
		 * @return {@code Builder} instance
		 */
		public Builder firstName(String firstName) {
			if (StringUtils.hasText(firstName)) {
				this.firstName = firstName.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter firstName failed");
			}
			return this;
		}

		/**
		 * Sets the {@code User.lastName} property
		 *
		 * @param lastName
		 * @return {@code Builder} instance
		 */
		public Builder lastName(String lastName) {
			if (StringUtils.hasText(lastName)) {
				this.lastName = lastName.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter lastName failed");
			}
			return this;
		}

		/**
		 * Sets the {@code User.email} property
		 *
		 * @param email
		 * @return {@code Builder} instance
		 */
		public Builder email(String email) {
			if (StringUtils.hasText(email)) {
				this.email = email.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter email failed");
			}
			return this;
		}

		/**
		 * Sets the {@code User.password} property
		 *
		 * @param password
		 * @return {@code Builder} instance
		 */
		public Builder password(String password) {
			if (StringUtils.hasText(password)) {
				this.password = password;
			} else {
				throw new ValidationException("Validation in Builder for parameter password failed");
			}
			return this;
		}

		/**
		 * Sets the {@code User.id} property
		 *
		 * @param id
		 * @return {@code Builder} instance
		 */
		public Builder id(String id) {
			if (StringUtils.hasText(id)) {
				this.id = id;
			} else {
				throw new ValidationException("Validation in Builder for parameter id failed");
			}
			return this;
		}

		/**
		 * Sets the {@code User.administrator} property
		 *
		 * @param firstName
		 * @return {@code Builder} instance
		 */
		public Builder isAdministrator(boolean administrator) {
			this.administrator = administrator;
			return this;
		}

		/**
		 * Sets the {@code User.creationDate} property
		 *
		 * @param creationDate
		 * @return {@code Builder} instance
		 */
		public Builder creationDate(Date creationDate) {
			if (null != creationDate) {
				this.creationDate = creationDate;
			} else {
				this.creationDate = new Date();
			}
			return this;
		}

		/**
		 * Sets the {@code User.selections} firstName property
		 *
		 * @param selections
		 * @return {@code Builder} instance
		 */
		public Builder selections(Set<SelectionDTO> selections) {

			final Set<Selection> set = new HashSet<Selection>();

			if (null == selections) {
				this.selections = set;
				return this;
			}

			selections.forEach(selection -> {
				if (StringUtils.hasText(selection.getVoteId()) && StringUtils.hasText(selection.getVotedOptionId())) {
					set.add(new Selection(selection.getVoteId(), selection.getVotedOptionId()));
				}
			});

			this.selections = set;
			return this;
		}

		/**
		 * Creates the actual {@code User} instance.
		 *
		 * @return the {@code User} entity
		 * @throws ValidationException
		 *             if a required value is not present
		 */
		public User build() throws ValidationException {
			return new User(this);
		}
	}

}
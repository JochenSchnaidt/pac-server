package com.prodyna.pac.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.OptionDTO;
import com.prodyna.pac.exception.ValidationException;

/**
 * Entity class to represent a vote. Creation and update of entities only via {@code Builder}.
 * <p>
 * This class is annotated to work with MongoDB.
 */
@Document(collection = "votes")
public class Vote {

	@Id
	private String id;
	private String topic;
	private String description;
	private Set<Option> options;
	private boolean editable;
	private Date creationDate;
	private String createdBy;
	private String createdByUserName;

	// necessary for automatic mapping
	public Vote() {
		super();
	}

	/**
	 * Private constructor to prevent instantiation of entity without {@code Builder}
	 *
	 * @param builder
	 *            preinitialized {@code Builder}
	 */
	private Vote(Builder builder) {

		if (null == builder) {
			throw new ValidationException("Validation in Vote for parameter builder failed");
		}

		if (StringUtils.hasText(builder.id)) {
			this.id = builder.id;
		}

		this.topic = builder.topic;
		this.description = builder.description;
		this.options = builder.options;
		this.editable = builder.editable;
		this.createdBy = builder.createdBy;
		this.creationDate = builder.creationDate;
		this.createdByUserName = builder.createdByUserName;
	}

	public String getId() {
		return id;
	}

	public String getTopic() {
		return topic;
	}

	public String getDescription() {
		return description;
	}

	public boolean isEditable() {
		return editable;
	}

	public Set<Option> getOptions() {
		return options;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", topic=" + topic + ", description=" + description + ", options=" + options + ", editable=" + editable + ", creationDate=" + creationDate + ", createdBy="
		        + createdBy + ", createdByUserName=" + createdByUserName + "]";
	}

	/**
	 * Builder for {@code Vote}
	 */
	public static class Builder {

		private String id;
		private String topic;
		private String description;
		private Set<Option> options;
		private boolean editable = true;
		private String createdBy;
		private Date creationDate;
		private String createdByUserName;

		/**
		 * Instantiate raw {@code Builder} instance
		 */
		public Builder() {
			creationDate(null);
		}

		/**
		 * Sets the {@code Vote.topic} property
		 *
		 * @param topic
		 * @return {@code Builder} instance
		 */
		public Builder topic(String topic) {
			if (StringUtils.hasText(topic)) {
				this.topic = topic.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter topic failed");
			}
			return this;
		}

		/**
		 * Sets the {@code Vote.description} property
		 *
		 * @param description
		 * @return {@code Builder} instance
		 */
		public Builder description(String description) {
			if (StringUtils.hasText(description)) {
				this.description = description.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter description failed");
			}
			return this;
		}

		/**
		 * Sets the {@code Vote.creationDate} property
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
		 * Sets the {@code Vote.id} property
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
		 * Sets the {@code Vote.options} property
		 *
		 * @param options
		 * @return {@code Builder} instance
		 */
		public Builder options(Set<OptionDTO> options) {

			final Set<Option> set = new HashSet<Option>();

			if (null == options) {
				this.options = set;
				return this;
			}

			options.forEach(option -> {
				if (StringUtils.hasText(option.getId())) {
					set.add(new Option(option.getId(), option.getName(), option.getCounter()));
				} else {
					set.add(new Option(option.getName(), option.getCounter()));
				}
			});

			this.options = set;
			return this;
		}

		/**
		 * Sets the {@code Vote.editable} property
		 *
		 * @param editable
		 * @return {@code Builder} instance
		 */
		public Builder editable(boolean editable) {
			this.editable = editable;
			return this;
		}

		/**
		 * Sets the {@code Vote.createdBy} property
		 *
		 * @param createdBy
		 * @return {@code Builder} instance
		 */
		public Builder createdBy(String createdBy) {

			if (StringUtils.hasText(createdBy)) {
				this.createdBy = createdBy;
			} else {
				throw new ValidationException("Validation in Builder for parameter createdBy failed");
			}
			return this;
		}

		/**
		 * Sets the {@code Vote.createdByUserName} property
		 *
		 * @param createdByUserName
		 * @return {@code Builder} instance
		 */
		public Builder createdByUserName(String createdByUserName) {

			if (StringUtils.hasText(createdByUserName)) {
				this.createdByUserName = createdByUserName;
			} else {
				throw new ValidationException("Validation in Builder for parameter createdByUserName failed");
			}
			return this;
		}

		/**
		 * Creates the actual {@code Vote} instance.
		 *
		 * @return the {@code User} entity
		 * @throws ValidationException
		 *             if a required value is not present
		 */
		public Vote build() throws ValidationException {
			return new Vote(this);
		}
	}

}

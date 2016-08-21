package com.prodyna.pac.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import com.prodyna.pac.dto.OptionDTO;
import com.prodyna.pac.exception.ValidationException;

@Document(collection = "votes")
public class Vote {

    @Id
    private String      id;
    private String      topic;
    private String      description;
    private Set<Option> options;
    private boolean     editable;
    private Date        creationDate;
    private String      createdBy;

    // necessary for automatic mapping
    public Vote() {
        super();
    }

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

    @Override
    public String toString() {
        return "Vote [id=" + id + ", topic=" + topic + ", description=" + description + ", options=" + options + ", editable=" + editable + ", creationDate=" + creationDate + ", createdBy="
                + createdBy + "]";
    }

    public static class Builder {

		private String id;
		private String topic;
		private String description;
		private Set<Option> options; 
		private boolean editable = true;
		private String createdBy;
		private Date creationDate;

        public Builder() {
            creationDate(null);
        }

		public Builder topic(String topic) {
			if (StringUtils.hasText(topic)) {
				this.topic = topic.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter topic failed");
			}
			return this;
		}

		public Builder description(String description) {
			if (StringUtils.hasText(description)) {
				this.description = description.trim();
			} else {
				throw new ValidationException("Validation in Builder for parameter description failed");
			}
			return this;
		}

		public Builder creationDate(Date creationDate) {
			if (null != creationDate) {
				this.creationDate = creationDate;
			} else {
				this.creationDate = new Date();
			}
			return this;
		}

		public Builder id(String id) {
			if (StringUtils.hasText(id)) {
				this.id = id;
			} else {
				throw new ValidationException("Validation in Builder for parameter id failed");
			}
			return this;
		}

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

		public Builder editable(boolean editable) {
			this.editable = editable;
			return this;
		}

		public Builder createdBy(String createdBy) {

			if (StringUtils.hasText(createdBy)) {
				this.createdBy = createdBy;
			} else {
				throw new ValidationException("Validation in Builder for parameter createdBy failed");
			}
			return this;
		}

		public Vote build() throws ValidationException {
			return new Vote(this);
		}
	}

}

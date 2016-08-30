package com.prodyna.pac.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * System wide DTO for a vote entity from persistence layer.
 * 
 * @see BaseDTO
 */
public class VoteDTO extends BaseDTO {

	private String id;
	private String topic;
	private String description;
	private Set<OptionDTO> options = new HashSet<OptionDTO>();
	private boolean editable;
	private Date creationDate;
	private String createdBy;
	private String createdByUserName;

	// explicit default constructor for JSON mapping
	public VoteDTO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<OptionDTO> getOptions() {
		return options;
	}

	public void setOptions(Set<OptionDTO> options) {
		this.options = options;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	@Override
	public String toString() {
		return "VoteDTO [id=" + id + ", topic=" + topic + ", description=" + description + ", options=" + options + ", editable=" + editable + ", creationDate=" + creationDate + ", createdBy="
		        + createdBy + ", createdByUserName=" + createdByUserName + "]";
	}

}

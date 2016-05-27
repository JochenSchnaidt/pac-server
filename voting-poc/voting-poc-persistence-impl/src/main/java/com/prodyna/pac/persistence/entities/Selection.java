package com.prodyna.pac.persistence.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Selection {

	private String voteId;
	private String votedOptionId;

	// necessary for automatic mapping
	public Selection() {
		super();
	}

	public Selection(String voteId, String votedOptionId) {
		super();
		this.voteId = voteId;
		this.votedOptionId = votedOptionId;
	}

	public String getVoteId() {
		return voteId;
	}

	public void setVoteId(String voteId) {
		this.voteId = voteId;
	}

	public String getVotedOptionId() {
		return votedOptionId;
	}

	public void setVotedOptionId(String votedOptionId) {
		this.votedOptionId = votedOptionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((voteId == null) ? 0 : voteId.hashCode());
		result = prime * result + ((votedOptionId == null) ? 0 : votedOptionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Selection other = (Selection) obj;
		if (voteId == null) {
			if (other.voteId != null) {
				return false;
			}
		} else if (!voteId.equals(other.voteId)) {
			return false;
		}
		if (votedOptionId == null) {
			if (other.votedOptionId != null) {
				return false;
			}
		} else if (!votedOptionId.equals(other.votedOptionId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Selection [voteId=" + voteId + ", votedOptionId=" + votedOptionId + "]";
	}

}

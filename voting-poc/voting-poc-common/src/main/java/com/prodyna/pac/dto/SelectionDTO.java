package com.prodyna.pac.dto;

/**
 * System wide DTO for a user's selection on a vote.
 */
public class SelectionDTO {

	private String voteId;
	private String votedOptionId;

	// explicit default constructor for JSON mapping
	public SelectionDTO() {
		super();
	}

	public SelectionDTO(String voteId, String votedOptionId) {
		this.voteId = voteId;
		this.votedOptionId = votedOptionId;
	}

	public String getVoteId() {
		return voteId;
	}

	public String getVotedOptionId() {
		return votedOptionId;
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
		SelectionDTO other = (SelectionDTO) obj;
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
		return "SelectionDTO [voteId=" + voteId + ", votedOptionId=" + votedOptionId + "]";
	}

}

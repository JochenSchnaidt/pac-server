package com.prodyna.pac.dto;

public class VotingDTO {

    private String voteId;
    private String userId;
    private String optionId;

    // explicit default constructor for JSON mapping
    public VotingDTO() {
        super();
    }

    public VotingDTO(String voteId, String userId, String optionId) {
        super();
        this.voteId = voteId;
        this.userId = userId;
        this.optionId = optionId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    @Override
    public String toString() {
        return "VotingDTO [voteId=" + voteId + ", userId=" + userId + ", optionId=" + optionId + "]";
    }

}

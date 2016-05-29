package com.prodyna.pac.auth;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    /**
     * Generated default serial version UID
     */
    private static final long serialVersionUID = 1L;

    private final String votingAuthenticationToken;

    public JwtAuthenticationResponse(String votingAuthenticationToken) {
        super();
        this.votingAuthenticationToken = votingAuthenticationToken;
    }

    public String getVotingAuthenticationToken() {
        return votingAuthenticationToken;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationResponse [votingAuthenticationToken=" + votingAuthenticationToken + "]";
    }

}

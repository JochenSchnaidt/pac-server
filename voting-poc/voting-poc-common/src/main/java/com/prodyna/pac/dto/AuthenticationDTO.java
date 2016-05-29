package com.prodyna.pac.dto;

public class AuthenticationDTO {

    private final String userId;
    private final String email;
    private final boolean authenticated;

    public AuthenticationDTO(String userId, String email, boolean authenticated) {
        super();
        this.userId = userId;
        this.email = email;
        this.authenticated = authenticated;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public String toString() {
        return "AuthenticationDTO [userId=" + userId + ", email=" + email + ", authenticated=" + authenticated + "]";
    }

}

package com.prodyna.pac.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class UserDTO extends BaseDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean administrator;
    private Set<SelectionDTO> selections = new HashSet<SelectionDTO>();
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    private Date creationDate;

    // explicit default constructor for JSON mapping
    public UserDTO() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public Set<SelectionDTO> getSelections() {
        return selections;
    }

    public void setSelections(Set<SelectionDTO> selections) {
        this.selections = selections;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", administrator=" + administrator + ", selections="
                + selections + ", authorities=" + authorities + ", creationDate=" + creationDate + ", operationResult=" + operationResult + "]";
    }

}
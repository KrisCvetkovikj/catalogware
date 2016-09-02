package com.wp.finki.ukim.mk.catalogware.model.response;

import com.wp.finki.ukim.mk.catalogware.model.User;

import java.io.Serializable;

/**
 * Created by Borce on 26.08.2016.
 */
public class AuthenticateUserResponse implements Serializable {

    private String token;
    private String name;
    private User.Role role;

    public AuthenticateUserResponse() {
    }

    public AuthenticateUserResponse(String token, String name, User.Role role) {
        this.token = token;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}

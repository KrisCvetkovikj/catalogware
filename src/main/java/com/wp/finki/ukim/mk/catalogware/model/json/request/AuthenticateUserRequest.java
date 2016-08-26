package com.wp.finki.ukim.mk.catalogware.model.json.request;

import java.io.Serializable;

/**
 * Created by Borce on 26.08.2016.
 */
public class AuthenticateUserRequest implements Serializable {

    private String email;
    private String password;

    public AuthenticateUserRequest() {
    }

    public AuthenticateUserRequest(String username, String password) {
        this.email = username;
        this.password = password;
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
}

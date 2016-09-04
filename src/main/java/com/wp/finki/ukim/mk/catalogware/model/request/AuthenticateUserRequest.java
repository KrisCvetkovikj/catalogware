package com.wp.finki.ukim.mk.catalogware.model.request;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Borce on 26.08.2016.
 */
public class AuthenticateUserRequest implements Serializable {

    @NotNull(message = "Email field is required")
    private String email;

    @NotNull(message = "Password field is required")
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

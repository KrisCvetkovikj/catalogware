package com.wp.finki.ukim.mk.catalogware.model.response;

import java.io.Serializable;

/**
 * Created by Borce on 26.08.2016.
 */
public class AuthenticateUserResponse implements Serializable {

    private String token;

    public AuthenticateUserResponse() {
    }

    public AuthenticateUserResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

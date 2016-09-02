package com.wp.finki.ukim.mk.catalogware.model.request;

/**
 * Created by Borce on 02.09.2016.
 */
public class RegisterUserRequest {

    private String name;
    private String email;
    private String password;
    private String repeatPassword;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String name, String email, String password, String  repeatPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}

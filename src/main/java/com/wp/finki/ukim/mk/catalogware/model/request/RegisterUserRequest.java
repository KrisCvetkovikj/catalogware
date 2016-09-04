package com.wp.finki.ukim.mk.catalogware.model.request;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by Borce on 02.09.2016.
 */
public class RegisterUserRequest {

    @NotNull(message = "Name field is required")
    private String name;

    @NotNull(message = "Email field is required")
    @Email(message = "The given email is not valid")
    private String email;

    @NotNull(message = "Password field is required")
    private String password;

    @NotNull(message = "Repeat Password field is required")
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

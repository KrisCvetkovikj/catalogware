package com.wp.finki.ukim.mk.catalogware.validator;

import com.wp.finki.ukim.mk.catalogware.model.request.RegisterUserRequest;
import com.wp.finki.ukim.mk.catalogware.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Borce on 02.09.2016.
 */
@Component
public class RegisterUserRequestValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RegisterUserRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterUserRequest request = (RegisterUserRequest) target;
        validateEmail(request.getEmail(), errors);
        validatePassword(request.getPassword(), request.getRepeatPassword(), errors);
    }

    private void validateEmail(String email, Errors errors) {
        if (userService.findByEmail(email) != null) {
            errors.rejectValue("email", "notUnique", "User with the given email already exists");
        }
    }

    private void validatePassword(String password, String repeatPassword, Errors errors) {
        if (password != null && repeatPassword != null && !(password.equals(repeatPassword))) {
            errors.rejectValue("password", "notMatch", "The given passwords don't match");
        }
    }
}

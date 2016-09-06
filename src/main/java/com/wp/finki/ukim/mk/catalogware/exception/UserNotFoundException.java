package com.wp.finki.ukim.mk.catalogware.exception;

/**
 * Created by Borce on 03.09.2016.
 */
public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

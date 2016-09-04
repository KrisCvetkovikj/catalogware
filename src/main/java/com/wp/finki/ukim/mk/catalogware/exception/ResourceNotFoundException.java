package com.wp.finki.ukim.mk.catalogware.exception;

/**
 * Created by Borce on 03.09.2016.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

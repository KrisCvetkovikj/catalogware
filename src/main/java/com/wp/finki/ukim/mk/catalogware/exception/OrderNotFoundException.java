package com.wp.finki.ukim.mk.catalogware.exception;

/**
 * Created by Borce on 04.09.2016.
 */
public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}

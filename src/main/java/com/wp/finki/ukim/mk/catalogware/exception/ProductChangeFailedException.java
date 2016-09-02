package com.wp.finki.ukim.mk.catalogware.exception;

/**
 * Created by Borce on 02.09.2016.
 */
public class ProductChangeFailedException extends RuntimeException {

    private String status;
    private String message;

    public ProductChangeFailedException(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

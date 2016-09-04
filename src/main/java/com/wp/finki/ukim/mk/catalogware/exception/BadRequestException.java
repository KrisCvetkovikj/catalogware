package com.wp.finki.ukim.mk.catalogware.exception;

import java.util.Map;

/**
 * Created by Borce on 02.09.2016.
 */
public class BadRequestException extends RuntimeException {

    private Map<String, String> errors;

    public BadRequestException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
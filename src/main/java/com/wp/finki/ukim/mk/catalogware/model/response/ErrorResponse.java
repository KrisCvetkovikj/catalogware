package com.wp.finki.ukim.mk.catalogware.model.response;

import java.util.Map;

/**
 * Created by Borce on 02.09.2016.
 */
public class ErrorResponse extends Response {

    private Map<String, String> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(int code, String status, String message, Map<String, String> errors) {
        super(code, status, message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}

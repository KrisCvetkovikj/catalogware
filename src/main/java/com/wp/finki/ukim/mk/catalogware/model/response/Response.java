package com.wp.finki.ukim.mk.catalogware.model.response;

/**
 * Created by Borce on 02.09.2016.
 */
public class Response {

    private int code;
    private String status;
    private String message;

    public Response() {
    }

    public Response(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
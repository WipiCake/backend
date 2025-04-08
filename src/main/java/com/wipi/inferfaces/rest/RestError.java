package com.wipi.inferfaces.rest;

public class RestError {
    private String error;
    private String message;

    public RestError(String error, String message) {
        this.error = error;
        this.message = message;
    }

}

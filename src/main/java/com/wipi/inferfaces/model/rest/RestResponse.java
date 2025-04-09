package com.wipi.inferfaces.model.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestResponse<T> {

    private int status;
    private String message;
    private T data;

    public RestResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public RestResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public RestResponse(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }


}

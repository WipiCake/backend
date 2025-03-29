package com.wipi.model.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestError {

    private String id;

    private String errorCode;

    private String message;

    private String detailed;

    private String link;

    private String title;

    private String redirect;

    private Map<String, String> validationError;

    public RestError() { }

    public RestError(String id, String message) {

        this.id = id;
        this.message = message;
    }

    public RestError(String id, String errorCode, String message) {

        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
    }

    public RestError(String id, String errorCode, Map<String,String> validationError) {

        this.id = id;
        this.errorCode = errorCode;
        this.validationError = validationError;
    }

}
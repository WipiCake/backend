package com.wipi.inferfaces.model.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponseEntity {
    public static <T> ResponseEntity<RestResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new RestResponse<>(HttpStatus.OK, message, data));
    }

    public static <T> ResponseEntity<RestResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestResponse<>(HttpStatus.CREATED, message, data));
    }


}

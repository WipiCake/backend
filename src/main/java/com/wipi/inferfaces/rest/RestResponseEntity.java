package com.wipi.inferfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponseEntity {
    public static <T> ResponseEntity<RestResponse<T>> ok(String message, T data) {
        return ResponseEntity.ok(new RestResponse<>(HttpStatus.OK, message, data));
    }

    public static <T> ResponseEntity<RestResponse<T>> created(String message, T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestResponse<>(HttpStatus.CREATED, message, data));
    }

    public static <T> ResponseEntity<RestResponse<T>> badRequest(String message, T data) {
        return ResponseEntity.badRequest().body(new RestResponse<>(HttpStatus.BAD_REQUEST, message, data));
    }

    public static <T> ResponseEntity<RestResponse<T>> error(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(new RestResponse<>(status, message, data));
    }

    public static <T> ResponseEntity<RestResponse<T>> of(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(new RestResponse<>(status, message, data));
    }


}

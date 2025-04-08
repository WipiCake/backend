package com.wipi.support;

import com.wipi.inferfaces.rest.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestError> handleRuntimeException(RuntimeException e) {
        RestError error = new RestError("RuntimeException", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    // 404 - 리소스 없음 (예: 잘못된 URL)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestError> handleNotFoundException(NoHandlerFoundException e) {
        RestError error = new RestError("NotFound", "요청하신 리소스를 찾을 수 없습니다.");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    // 500 - 서버 내부 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestError> handleException(Exception e) {
        RestError error = new RestError("InternalServerError", "서버 내부 오류가 발생했습니다.");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

}

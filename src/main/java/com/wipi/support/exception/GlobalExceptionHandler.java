package com.wipi.support.exception;

import com.wipi.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 400 Bad Request - @Valid 또는 @Validated 실패 (Form 방식)
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> bindException(BindException e) {
        String message = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        String fullMessages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.warn("BindException 발생: {}", fullMessages);

        return ApiResponse.fail(
                HttpStatus.BAD_REQUEST.value(),
                message
        );
    }

    /**
     * 400 Bad Request - JSON 파싱 실패 (RequestBody 깨졌을 때)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleMessageNotReadable(HttpMessageNotReadableException e) {
        return ApiResponse.fail(
                HttpStatus.BAD_REQUEST.value(),
                "요청 본문을 읽을 수 없습니다: " + e.getMessage()
        );
    }

    /**
     * 401 Unauthorized - 인증 실패 (예: Spring Security 로그인 실패)
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Object> handleAuthenticationException(AuthenticationException e) {
        return ApiResponse.fail(
                HttpStatus.UNAUTHORIZED.value(),
                "인증에 실패했습니다: " + e.getMessage()
        );
    }

    /**
     * 403 Forbidden - 인가 실패 (권한 없음)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Object> handleAccessDeniedException(AccessDeniedException e) {
        return ApiResponse.fail(
                HttpStatus.FORBIDDEN.value(),
                "접근이 거부되었습니다."
        );
    }

    /**
     * 404 Not Found - 잘못된 URL 요청 (매핑되는 핸들러 없음)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNotFound(NoHandlerFoundException e) {
        return ApiResponse.fail(
                HttpStatus.NOT_FOUND.value(),
                "요청한 경로를 찾을 수 없습니다."
        );
    }

    /**
     * 405 Method Not Allowed - 지원되지 않는 HTTP 메서드 (예: GET 대신 POST 요청)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse<Object> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        return ApiResponse.fail(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "지원되지 않는 HTTP 메서드입니다: " + e.getMethod()
        );
    }

    /**
     * 409 Conflict - 데이터 무결성 위반 (예: 유니크 제약 조건 위반)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ApiResponse.fail(
                HttpStatus.CONFLICT.value(),
                "데이터 무결성 오류: " + e.getMostSpecificCause().getMessage()
        );
    }

    /**
     * 415 Unsupported Media Type - 지원되지 않는 Content-Type 요청 (예: text/plain으로 JSON 요청 등)
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiResponse<Object> handleUnsupportedMediaType(HttpMediaTypeNotSupportedException e) {
        return ApiResponse.fail(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                "지원하지 않는 미디어 타입입니다: " + e.getContentType()
        );
    }

    /**
     * 429 Too Many Requests - 커스텀 제한 예외 처리 (Rate Limiting 등)
     */
    @ExceptionHandler(TooManyRequestsException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ApiResponse<Object> handleTooManyRequests(TooManyRequestsException e) {
        return ApiResponse.fail(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                e.getMessage()
        );
    }

    /**
     * 500 Internal Server Error - 런타임 예외 (명시적 처리되지 않은 RuntimeException)
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleRuntimeException(RuntimeException e) {
        return ApiResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "서버 오류가 발생했습니다: " + e.getMessage()
        );
    }

    /**
     * 500 Internal Server Error - 그 외 모든 예외 처리 (가장 마지막 catch-all)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception e) {
        return ApiResponse.fail(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "예기치 못한 오류가 발생했습니다."
        );
    }
}

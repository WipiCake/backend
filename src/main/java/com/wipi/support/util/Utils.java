package com.wipi.support.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class Utils {

    private final ObjectMapper injectedMapper;
    private static ObjectMapper objectMapper;


    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String EXPIRED_JWT_CLEANUP_CRON = "0 0 */4 * * ?";

    @PostConstruct
    public void init() {
        // LocalDateTime 등 ISO 포맷을 위한 모듈 등록
        injectedMapper.registerModule(new JavaTimeModule());
        injectedMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        objectMapper = this.injectedMapper;
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("JSON 변환 오류: {}", e.getMessage(), e);
            return null;
        }
    }

    public static String generate32CharCode() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}

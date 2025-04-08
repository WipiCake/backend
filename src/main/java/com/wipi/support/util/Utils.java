package com.wipi.support.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Utils {

    private final ObjectMapper injectedMapper;
    private static ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
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
}

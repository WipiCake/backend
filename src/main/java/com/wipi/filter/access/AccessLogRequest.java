package com.wipi.filter.access;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class AccessLogRequest {
    private String method;
    private String uri;
    private String query;
    private String requestBody;
    private String responseBody;
    private String headers;
    private String userAgent;
    private String remoteIp;
    private int status;
    private String threadName;
    private LocalDateTime requestAt;
    private LocalDateTime responseAt;
    private long durationMs;

    public static String extractClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}

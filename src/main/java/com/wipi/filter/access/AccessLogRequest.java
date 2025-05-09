package com.wipi.filter.access;

import com.wipi.infra.access.AccessLogConsumerCommand;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    private AccessLogRequest(String method, String uri, String query, String requestBody, String responseBody,
                             String headers, String userAgent, String remoteIp, int status, String threadName,
                             LocalDateTime requestAt, LocalDateTime responseAt, long durationMs) {
        this.method = method;
        this.uri = uri;
        this.query = query;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.headers = headers;
        this.userAgent = userAgent;
        this.remoteIp = remoteIp;
        this.status = status;
        this.threadName = threadName;
        this.requestAt = requestAt;
        this.responseAt = responseAt;
        this.durationMs = durationMs;
    }

    public static AccessLogRequest of(String method, String uri, String query,
                                      String requestBody, String responseBody,
                                      String headers, String userAgent, String remoteIp,
                                      int status, String threadName,
                                      LocalDateTime requestAt, LocalDateTime responseAt) {

        long durationMs = Duration.between(requestAt, responseAt).toMillis();

        return new AccessLogRequest(
                method, uri, query,
                requestBody, responseBody,
                headers, userAgent, remoteIp,
                status, threadName,
                requestAt, responseAt, durationMs
        );
    }

    public static String extractClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isEmpty()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    public AccessLogConsumerCommand.Save toCommand() {
        return AccessLogConsumerCommand.Save.of(
                method,
                uri,
                query,
                requestBody,
                responseBody,
                headers,
                userAgent,
                remoteIp,
                status,
                threadName,
                requestAt,
                responseAt,
                durationMs
        );
    }

}

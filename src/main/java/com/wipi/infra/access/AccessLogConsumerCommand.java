package com.wipi.infra.access;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessLogConsumerCommand {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Save {
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

        public static Save of(String method, String uri, String query,
                              String requestBody, String responseBody,
                              String headers, String userAgent, String remoteIp,
                              int status, String threadName,
                              LocalDateTime requestAt, LocalDateTime responseAt,
                              long durationMs) {

            Save save = new Save();
            save.method = method;
            save.uri = uri;
            save.query = query;
            save.requestBody = requestBody;
            save.responseBody = responseBody;
            save.headers = headers;
            save.userAgent = userAgent;
            save.remoteIp = remoteIp;
            save.status = status;
            save.threadName = threadName;
            save.requestAt = requestAt;
            save.responseAt = responseAt;
            save.durationMs = durationMs;
            return save;
        }

    }
}

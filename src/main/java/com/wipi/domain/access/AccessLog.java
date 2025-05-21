package com.wipi.domain.access;

import com.wipi.filter.access.AccessLogRequest;
import com.wipi.infra.access.AccessLogConsumerCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wipi_access_log")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_log_id")
    private Long id;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private String uri;

    private String query;

    @Lob
    private String requestBody;

    @Lob
    private String responseBody;

    @Lob
    private String headers;

    private String userAgent;

    @Column(name = "remote_ip")
    private String remoteIp;

    private int status;

    private String threadName;

    private LocalDateTime requestAt;

    private LocalDateTime responseAt;

    private long durationMs;

    private AccessLog(String method, String uri, String query,
                      String requestBody, String responseBody,
                      String headers, String userAgent, String remoteIp,
                      int status, String threadName,
                      LocalDateTime requestAt, LocalDateTime responseAt,
                      long durationMs) {
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

    public static AccessLog of(AccessLogRequest dto) {
        return new AccessLog(
                dto.getMethod(),
                dto.getUri(),
                dto.getQuery(),
                dto.getRequestBody(),
                dto.getResponseBody(),
                dto.getHeaders(),
                dto.getUserAgent(),
                dto.getRemoteIp(),
                dto.getStatus(),
                dto.getThreadName(),
                dto.getRequestAt(),
                dto.getResponseAt(),
                dto.getDurationMs()
        );
    }

    public static AccessLog of(AccessLogConsumerCommand.Save save) {
        return new AccessLog(
                save.getMethod(),
                save.getUri(),
                save.getQuery(),
                save.getRequestBody(),
                save.getResponseBody(),
                save.getHeaders(),
                save.getUserAgent(),
                save.getRemoteIp(),
                save.getStatus(),
                save.getThreadName(),
                save.getRequestAt(),
                save.getResponseAt(),
                save.getDurationMs()
        );
    }
}

package com.wipi.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogParam {
    private String userId;
    private Long accessLogId;
    private String method;
    private String uri;
    private String queryString;
    private String queryParams;
    private String requestBody;
    private String headers;
    private String userAgent;
    private String referer;
    private String clientIp;
    private String host;
    private String authorization;
    private LocalDateTime requestAt;
    private String threadId;
    private LocalDateTime responseAt;
    private String responseBody;
    private String status;
    private Integer statusCode;
    private Long elapsed;

    public AccessLogParam(String method, String uri, String queryString, String queryParams, String requestBody,
                          String headers, String userAgent, String referer, String clientIp, String host,
                          String authorization, LocalDateTime requestAt, String threadId, LocalDateTime responseAt,
                          String responseBody, String status, Integer statusCode, Long elapsed) {
        this.method = method;
        this.uri = uri;
        this.queryString = queryString;
        this.queryParams = queryParams;
        this.requestBody = requestBody;
        this.headers = headers;
        this.userAgent = userAgent;
        this.referer = referer;
        this.clientIp = clientIp;
        this.host = host;
        this.authorization = authorization;
        this.requestAt = requestAt;
        this.threadId = threadId;
        this.responseAt = responseAt;
        this.responseBody = responseBody;
        this.status = status;
        this.statusCode = statusCode;
        this.elapsed = elapsed;
    }

    public AccessLogParam(String method, String requestURI, String queryString, String s, String reduce, String string, String header, String referer, String s1, String host, String authorization) {
    }
}
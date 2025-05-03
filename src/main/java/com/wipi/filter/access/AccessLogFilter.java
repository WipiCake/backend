package com.wipi.filter.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AccessLogFilter implements Filter {

    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        LocalDateTime requestAt = LocalDateTime.now();

        chain.doFilter(req, res);

        try {
            LocalDateTime responseAt = LocalDateTime.now();

            AccessLogRequest accessLog = AccessLogRequest.builder()
                    .method(req.getMethod())
                    .uri(req.getRequestURI())
                    .query(req.getQueryString())
                    .requestBody(new String(req.getContentAsByteArray(), StandardCharsets.UTF_8))
                    .responseBody(new String(res.getContentAsByteArray(), StandardCharsets.UTF_8))
                    .headers(Collections.list(req.getHeaderNames()).stream()
                            .collect(Collectors.toMap(h -> h, req::getHeader))
                            .toString())
                    .userAgent(req.getHeader("User-Agent"))
                    .remoteIp(AccessLogRequest.extractClientIp(req))
                    .status(res.getStatus())
                    .threadName(Thread.currentThread().getName())
                    .requestAt(requestAt)
                    .responseAt(responseAt)
                    .durationMs(Duration.between(requestAt, responseAt).toMillis())
                    .build();

            log.info("AccessLog: {}", objectMapper.writeValueAsString(accessLog));

        } catch (Exception logEx) {
            log.error("AccessLogFilter 로깅 중 오류 발생", logEx);
        } finally {
            res.copyBodyToResponse();
        }
    }

}

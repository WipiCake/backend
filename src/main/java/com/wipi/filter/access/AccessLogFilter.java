package com.wipi.filter.access;

import com.wipi.support.util.Utils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class AccessLogFilter implements Filter {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        LocalDateTime requestAt = LocalDateTime.now();

        chain.doFilter(req, res);

        LocalDateTime responseAt = LocalDateTime.now();

        String requestBody = new String(req.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(res.getContentAsByteArray(), StandardCharsets.UTF_8);

        String headers = Collections.list(req.getHeaderNames()).stream()
                .collect(Collectors.toMap(h -> h, req::getHeader))
                .toString();

        AccessLogRequest accessLog = AccessLogRequest.of(
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                requestBody,
                responseBody,
                headers,
                req.getHeader("User-Agent"),
                AccessLogRequest.extractClientIp(req),
                res.getStatus(),
                Thread.currentThread().getName(),
                requestAt,
                responseAt
        );

        log.info("AccessLog : {}", Utils.toJson(accessLog));
        rabbitTemplate.convertAndSend("exchange.access.log","route.access.log.save",accessLog.toCommand());
        res.copyBodyToResponse();

    }
}

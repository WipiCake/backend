package com.wipi.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final long TIME_WINDOW = 6000;
    private static final int MAX_REQUESTS = 80;
    private final ConcurrentHashMap<String, UserRequestParam> requestCounts = new ConcurrentHashMap<>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        requestCounts.putIfAbsent(clientIp, new UserRequestParam());
        UserRequestParam userRequestInfo = requestCounts.get(clientIp);

        synchronized (userRequestInfo) {
            if (currentTime - userRequestInfo.startTime > TIME_WINDOW) {
                userRequestInfo.startTime = currentTime;
                userRequestInfo.requestCount.set(0);
            }

            if (userRequestInfo.requestCount.incrementAndGet() > MAX_REQUESTS) {
                System.out.println("🚨 Rate Limit Exceeded for IP: " + clientIp);

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
                response.getWriter().write("Too many requests. Please try again later.");
                return false;
            }
        }
        //추가로 문자 or telegram or
        log.info("✅ Request Allowed for IP: " + clientIp + " (Count: " + userRequestInfo.requestCount.get() + ")");
        return true;
    }
}

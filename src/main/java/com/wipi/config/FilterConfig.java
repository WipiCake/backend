package com.wipi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipi.filter.AccessLogFilter;
import com.wipi.service.third.ThreadLocalAccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ThreadLocalAccessLogService threadLocalAccessLogService;

    @Bean
    public FilterRegistrationBean<AccessLogFilter> accessLogFilter() {
        FilterRegistrationBean<AccessLogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AccessLogFilter(rabbitTemplate,objectMapper,threadLocalAccessLogService));
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE); //1순위로 실행
        return registration;
    }
}

package com.wipi.support.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.main.rabbitmq")
public class RabbitmqProperties {

    private String host;
    private int port;
    private String username;
    private String password;
}

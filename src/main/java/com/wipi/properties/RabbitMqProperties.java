package com.wipi.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "spring.main.rabbitmq")
@Getter
@Setter
public class RabbitMqProperties {

    private String host;
    private int port;
    private String username;
    private String password;
}

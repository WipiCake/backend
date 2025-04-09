package com.wipi.support.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitmqProperties {

    private String host;
    private int port;
    private String username;
    private String password;
}

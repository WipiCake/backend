package com.wipi.support.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cool-sms.api")
public class CoolSmsProperties {
    private String key;
    private String secret;
    private String number;
}

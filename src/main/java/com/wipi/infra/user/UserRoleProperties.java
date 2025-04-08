package com.wipi.infra.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "role")
public class UserRoleProperties {

    private String user;
    private String admin;
    private String any;



}

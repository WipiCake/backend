package com.wipi.support.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT 서명에 사용할 비밀 키
     */
    private String secret;

    /**
     * access token 만료 시간 (분 단위)
     */
    private long accessExpirationMinutes;

    /**
     * refresh token 만료 시간 (일 단위)
     */
    private long refreshExpirationDays;

    /**
     * 쿠키 이름 (refresh token 저장용)
     */
    private String refreshCookieName;

    /**
     * JWT 이름 (access token 저장용)
     */
    private String accessHeaderName;

}

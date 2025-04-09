package com.wipi.infra.jwt;


import com.wipi.support.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final ZoneId zoneId = ZoneId.systemDefault();
    private SecretKey secretKey;

    @PostConstruct
    private void initSecretKey() {
        this.secretKey = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    // ===================== JWT 생성 & 파싱 =====================
    public String createAccessToken(String username, String role) {
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(jwtProperties.getAccessExpirationMinutes());

        return Jwts.builder()
                .claim("category", "access")
                .claim("username", username)
                .claim("role", role)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiredTime.atZone(zoneId).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String username, String role) {
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(jwtProperties.getRefreshExpirationDays());

        return Jwts.builder()
                .claim("category", "refresh")
                .claim("username", username)
                .claim("role", role)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiredTime.atZone(zoneId).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ===================== 만료 시간 =====================

    public LocalDateTime getExpirationFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().toInstant().atZone(zoneId).toLocalDateTime();
    }

    public LocalDateTime getRefreshExpiredTime() {
        return LocalDateTime.now().plusDays(jwtProperties.getRefreshExpirationDays());
    }

    // ===================== 쿠키 =====================

    public Cookie createRefreshCookie(String refreshToken) {
        int maxAge = (int) (jwtProperties.getRefreshExpirationDays() * 24 * 60 * 60); // 일 → 초 변환

        Cookie cookie = new Cookie(jwtProperties.getRefreshCookieName(), refreshToken);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie createLogoutCookie() {
        Cookie cookie = new Cookie(jwtProperties.getRefreshCookieName(), null);
        cookie.setMaxAge(0); // 삭제
        cookie.setPath("/");
        return cookie;
    }
}

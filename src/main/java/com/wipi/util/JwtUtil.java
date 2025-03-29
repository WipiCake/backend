package com.wipi.util;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final SecretKey secretKey;
    private final ZoneId zoneId = ZoneId.systemDefault();


    public JwtUtil(){
        String secret = "vmfhaltmskdlstkfkdgodyroqkfwkdbalroqkfwkdbalaaaaaaaaaaaaaaaabbbbb";
        secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("username",String.class);
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().get("role",String.class);
    }
    public boolean isExpired(String token){
        Date expirationDate = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload().getExpiration();

        LocalDateTime expiredAt = expirationDate.toInstant().atZone(zoneId).toLocalDateTime();
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public void isAccessCategory(String category){
        if(!category.equals("access")){
            log.warn("Invalid Access Token");
        }
    }

    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().
                parseSignedClaims(token).getPayload().
                get("category",String.class);
    }
    public LocalDateTime getAccessExpiredTime() {
        return LocalDateTime.now().plusMinutes(10);

    }
    public LocalDateTime getRefreshExpiredTime() {
        return LocalDateTime.now().plusDays(1);
    }

    public String createJwt(String category,String username, String role, LocalDateTime expiredTime){
        return Jwts.builder()
                .claim("category",category)
                .claim("username",username)
                .claim("role",role)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expiredTime.atZone(zoneId).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    public Cookie createCookie(String value){
        Cookie cookie = new Cookie("refresh",value);
        cookie.setMaxAge(24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie createLogoutCookie(){
        Cookie cookie = new Cookie("refresh",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }


}

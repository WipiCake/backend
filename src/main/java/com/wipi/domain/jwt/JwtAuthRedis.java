package com.wipi.domain.jwt;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "jwtToken")
public class JwtAuthRedis implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    private String accessToken;
    private String email;
    private String refreshToken;
    private LocalDateTime refreshExpiration;
    private LocalDateTime accessExpiration;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public JwtAuthRedis(String email, String refreshToken, LocalDateTime refreshExpiration, String accessToken, LocalDateTime accessExpiration, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.refreshExpiration = refreshExpiration;
        this.accessToken = accessToken;
        this.accessExpiration = accessExpiration;
        this.createAt = createdAt;
        this.updateAt = updateAt;
    }

    public JwtAuthRedis(String email, String refreshToken, LocalDateTime refreshExpiration, String accessToken, LocalDateTime accessExpiration) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.refreshExpiration = refreshExpiration;
        this.accessToken = accessToken;
        this.accessExpiration = accessExpiration;
    }
}

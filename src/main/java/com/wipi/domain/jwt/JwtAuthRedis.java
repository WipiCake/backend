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

    @Indexed
    private String refreshToken;

    private String email;
    private LocalDateTime refreshExpiration;
    private LocalDateTime accessExpiration;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}



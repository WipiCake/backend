package com.wipi.domain.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import jakarta.persistence.Id;
import org.springframework.data.redis.core.index.Indexed;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "EmailVerification")
public class EmailVerification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed
    private String email;
    private String verificationCode;
    private String purpose;
    private LocalDateTime expirationTime;
    private LocalDateTime createAt;
}

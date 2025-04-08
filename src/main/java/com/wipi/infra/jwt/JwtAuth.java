package com.wipi.infra.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wipi.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_jwt")
public class JwtAuth extends BaseTimeEntity {

    @Id
    @Column(length = 255)
    private String id;

    @Column(length = 255)
    private String accessToken;

    @Column(length = 255)
    private String refreshToken;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshExpiration;

    @Column(length = 255)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accessExpiration;


}

package com.wipi.domain.jwt;

import java.util.Optional;

public interface JwtRepository {

    Optional<JwtAuthRedis> findByAccessToken(String accessToken);
    Optional<JwtAuthRedis> findByRefreshToken(String refreshToken);
    void removeAccessToken(String accessToken);
    JwtAuthRedis saveJwtAuth(JwtAuthRedis jwtAuthRedis);

}

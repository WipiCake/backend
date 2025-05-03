package com.wipi.infra.jwt;

import com.wipi.domain.jwt.JwtAuthRedis;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface JwtRedisCachingRepository extends CrudRepository<JwtAuthRedis, String> {
    Optional<JwtAuthRedis> findJwtAuthRedisByAccessToken(String accessToken);
    Optional<JwtAuthRedis> findJwtAuthRedisByRefreshToken(String refreshToken);
    Optional<JwtAuthRedis> findJwtAuthRedisByEmail(String email);
    void removeJwtAuthRedisById(String id);
    void removeJwtAuthRedisByAccessToken(String accessToken);
    void removeJwtAuthRedisByRefreshToken(String refreshToken);
}

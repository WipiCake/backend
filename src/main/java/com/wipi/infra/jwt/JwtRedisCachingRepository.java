package com.wipi.infra.jwt;


import com.wipi.domain.jwt.JwtAuthRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface JwtRedisCachingRepository extends CrudRepository<JwtAuthRedis, String> {
    Optional<JwtAuthRedis> findByAccessToken(String accessToken);
    Optional<JwtAuthRedis> findByRefreshToken(String refreshToken);
}

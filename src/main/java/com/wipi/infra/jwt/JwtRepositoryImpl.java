package com.wipi.infra.jwt;

import com.wipi.domain.jwt.JwtAuthRedis;
import com.wipi.domain.jwt.JwtRepository;
import com.wipi.support.util.Utils;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtRedisCachingRepository jwtRedisCachingRepository;

    @Override
    public Optional<JwtAuthRedis> findByAccessToken(String accessToken) {
        return jwtRedisCachingRepository.findByAccessToken(accessToken);
    }

    @Override
    public Optional<JwtAuthRedis> findByRefreshToken(String refreshToken) {
        return jwtRedisCachingRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public void removeAccessToken(String accessToken) {
        jwtRedisCachingRepository.findByAccessToken(accessToken).ifPresent(entity -> {
            jwtRedisCachingRepository.deleteById(entity.getId());
            log.info("Removed Redis entry by accessToken (id={}): {}", entity.getId(), accessToken);
        });
    }

    @Override
    public JwtAuthRedis saveJwtAuth(JwtAuthRedis jwtAuthRedis) {
        JwtAuthRedis saved = jwtRedisCachingRepository.save(jwtAuthRedis);

        log.info("Saved JWT entity with UUID key: {}", Utils.toJson(saved));
        return saved;
    }

}

package com.wipi.infra.jwt;

import com.wipi.domain.jwt.JwtAuthRedis;
import com.wipi.domain.jwt.JwtRepository;
import com.wipi.support.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JwtRepositoryImpl implements JwtRepository {

    private final JwtRedisCachingRepository jwtRedisCachingRepository;

    @Override
    public Optional<JwtAuthRedis> findJwtAuthRedisByAccessToken(String accessToken) {
        return jwtRedisCachingRepository.findJwtAuthRedisByAccessToken(accessToken);
    }

    @Override
    public Optional<JwtAuthRedis> findJwtAuthRedisByRefreshToken(String refreshToken) {
        return jwtRedisCachingRepository.findJwtAuthRedisByRefreshToken(refreshToken);
    }

    @Override
    public void removeAccessToken(String accessToken) {
        jwtRedisCachingRepository.findJwtAuthRedisByAccessToken(accessToken).ifPresent(entity -> {
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

    @Override
    public List<JwtAuthRedis> findAllJwtAuthRedis() {
        Iterable<JwtAuthRedis> iterable = jwtRedisCachingRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

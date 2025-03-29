package com.wipi.service.third;

import com.wipi.model.entity.RedisJwtEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisCachingService {
    @Cacheable(value = "jwtToken", key = "#accessToken", unless = "#result == null")
    public RedisJwtEntity JwtValidationByAccessToken(String accessToken) {
        log.warn("사용자 정보를 찾을 수 없습니다.");
        return null;
    }

    @CacheEvict(value = "jwtToken", key = "#accessToken")
    public void removeCache(String accessToken){
        log.info("캐시 삭제: {}",accessToken);
    }

    @CachePut(value = "jwtToken", key = "#accessToken")
    public RedisJwtEntity saveJwtCache(String accessToken, RedisJwtEntity jwtEntity) {
        log.info("JWT 캐싱 저장: {}", jwtEntity);
        return jwtEntity;
    }



}

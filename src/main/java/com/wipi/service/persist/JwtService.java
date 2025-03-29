package com.wipi.service.persist;

import com.wipi.model.entity.AuthJwtEntity;
import com.wipi.repository.JwtTokenRepository;
import com.wipi.repository.RedisJwtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenRepository jwtTokenRepository;
    private final RedisJwtRepository redisJwtRepository;

    @Transactional
    public AuthJwtEntity saveJwtToken(AuthJwtEntity authJwtEntity){
        AuthJwtEntity resultAuthJwtEntity = jwtTokenRepository.save(authJwtEntity);

        log.info("JWT 저장 완료 : {}", resultAuthJwtEntity);
        return resultAuthJwtEntity;
    }
    @Transactional
    public void deleteByRefreshToken(String refresh){
        jwtTokenRepository.deleteByRefreshToken(refresh);
    }

    public AuthJwtEntity findByAccessToken(String accessToken){
        return jwtTokenRepository.findByAccessToken(accessToken).orElse(null);
    }

    public AuthJwtEntity findByRefreshToken(String refreshToken){
        return jwtTokenRepository.findByRefreshToken(refreshToken).orElse(null);
    }


}

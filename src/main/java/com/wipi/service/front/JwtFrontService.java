package com.wipi.service.front;

import com.wipi.exception.AuthRuntimeException;
import com.wipi.model.entity.AuthJwtEntity;
import com.wipi.model.entity.RedisJwtEntity;
import com.wipi.model.result.JwtTokenResponse;
import com.wipi.service.persist.JwtService;
import com.wipi.service.third.RedisCachingService;
import com.wipi.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtFrontService {

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final RedisCachingService redisCachingService;

    //Redis 캐싱 저장
    public JwtTokenResponse saveJwtToken(Authentication authResult){
        String username = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority();
        String accessToken = jwtUtil.createJwt("access", username, role, jwtUtil.getAccessExpiredTime());
        String refreshToken = jwtUtil.createJwt("refresh", username, role, jwtUtil.getRefreshExpiredTime());
        Cookie refreshCookie = jwtUtil.createCookie(refreshToken);

        RedisJwtEntity redisJwtEntity = new RedisJwtEntity(
                username,
                refreshToken,
                jwtUtil.getRefreshExpiredTime(),
                accessToken,
                jwtUtil.getAccessExpiredTime()
        );

        redisCachingService.saveJwtCache(accessToken,redisJwtEntity);
        return new JwtTokenResponse(accessToken,refreshCookie);
    }

    public RedisJwtEntity JwtValidationByAccessToken(String accessToken) {
        return redisCachingService.JwtValidationByAccessToken(accessToken);
    }

    public AuthJwtEntity reissueJwtToken(String refresh){
        if(jwtService.findByRefreshToken(refresh) == null){
            throw new AuthRuntimeException("RefreshToken Invalid");
        }
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        jwtService.deleteByRefreshToken(refresh);

        AuthJwtEntity authJwtEntity = new AuthJwtEntity(
                username,
                jwtUtil.createJwt("refresh",username,role,jwtUtil.getRefreshExpiredTime()),
                jwtUtil.getRefreshExpiredTime(),
                jwtUtil.createJwt("access",username,role,jwtUtil.getAccessExpiredTime()),
                jwtUtil.getAccessExpiredTime()
        );

        return jwtService.saveJwtToken(authJwtEntity);
    }

    public AuthJwtEntity findByRefreshToken(String refreshToken){

        return jwtService.findByRefreshToken(refreshToken);
    }

    public Cookie deleteAuth(String accessToken){
        redisCachingService.removeCache(accessToken);
        return jwtUtil.createLogoutCookie();
    }

}

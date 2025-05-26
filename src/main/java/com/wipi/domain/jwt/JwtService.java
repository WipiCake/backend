package com.wipi.domain.jwt;

import com.wipi.model.dto.res.ResIssueJwtDto;
import com.wipi.infra.jwt.JwtUtil;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtUtil jwtUtil;
    private final JwtRepository jwtRepository;
    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;

    public List<JwtAuthRedis> getJwtInfoAll(){
        return jwtRepository.findAllJwtAuthRedis();
    }

    public String ValidAccess(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            log.warn("accessToken header is missing or malformed");
            return null;
        }
        accessToken = accessToken.replaceFirst("^Bearer\\s+", "").trim();
        log.info("accessToken: {}", accessToken);

        JwtAuthRedis jwtAuth = jwtRepository.findJwtAuthRedisByAccessToken(accessToken).orElse(null);
        if (jwtAuth == null) {
            log.warn("accessToken not found in Redis");
            return null;
        }

        if(!accessToken.equals(jwtAuth.getAccessToken())) {
            log.warn("access token does not match");
            return null;
        }

        if (LocalDateTime.now().isAfter(jwtAuth.getAccessExpiration())) {
            log.warn("accessToken expired");
            return null;
        }

        return accessToken;
    }

    public ResIssueJwtDto issueJwtAuth(String userId, String role) {

        String access = jwtUtil.createAccessToken(userId, role);
        String refresh = jwtUtil.createRefreshToken(userId, role);

        JwtAuthRedis findJwtAuth = jwtRepository.findJwtAuthRedisByEmail(userId).orElse(null);

        JwtAuthRedis savedJwt = new JwtAuthRedis();
        savedJwt.setAccessToken(access);
        savedJwt.setRefreshToken(refresh);
        savedJwt.setEmail(userId);
        savedJwt.setAccessExpiration(jwtUtil.getExpirationFromToken(access));
        savedJwt.setRefreshExpiration(jwtUtil.getExpirationFromToken(refresh));

        if(findJwtAuth == null) {
            savedJwt.setId("JWT:" + UUID.randomUUID());
            savedJwt.setCreateAt(LocalDateTime.now());
        }else{
            savedJwt.setId(findJwtAuth.getId());
            savedJwt.setCreateAt(findJwtAuth.getCreateAt());
            savedJwt.setUpdateAt(LocalDateTime.now());
        }

        JwtAuthRedis jwtAuth = jwtRepository.saveOrUpdateJwtAuth(savedJwt);
        ResponseCookie cookie = jwtUtil.createRefreshCookie(refresh);


        return new ResIssueJwtDto(
                jwtAuth.getAccessToken(),
                jwtAuth.getRefreshToken(),
                cookie
        );
    }

    public String reissueAccessByRefresh(HttpServletRequest request) {
        String refreshToken = null;

        log.info("데이터 : {}", Utils.toJson(request.getCookies()));

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                log.info("cookie : {}", Utils.toJson(cookie.getName()));
                if (cookie.getName().equals(jwtProperties.getRefreshCookieName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if(request.getHeader("refresh-token") != null && refreshToken == null) {
            refreshToken = request.getHeader("refresh-token");
        }

        log.info("refrsh Token : {}", refreshToken);

        if (refreshToken == null) {
            log.warn("refresh token not found in header");
            return null;
        }

        JwtAuthRedis jwtAuth = jwtRepository.findJwtAuthRedisByRefreshToken(refreshToken).orElse(null);

        if (jwtAuth == null) {
            log.warn("refresh token not found in redis");
            return null;
        }

        if(!refreshToken.equals(jwtAuth.getRefreshToken())) {
            log.warn("refresh token does not match");
            return null;
        }

        if(jwtAuth.getRefreshExpiration().isBefore(LocalDateTime.now())) {
            log.warn("refresh expired");
            return null;
        }

        String reissueAccessToken = jwtUtil.createAccessToken(
                jwtUtil.getUsername(jwtAuth.getAccessToken()),
                jwtUtil.getRole(jwtAuth.getAccessToken())
        );

        jwtAuth.setAccessToken(reissueAccessToken);
        jwtAuth.setUpdateAt(LocalDateTime.now());
        jwtRepository.saveOrUpdateJwtAuth(jwtAuth);
        log.info("accessUpdate : {}", reissueAccessToken);

        return reissueAccessToken;
    }

    public Cookie logoutAndCreateExpiredCookie(HttpServletRequest request){
        // todo RefreshToken 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    jwtRepository.removeJwtAuthRedisByRefreshToken(cookie.getValue());
                    break;
                }
            }
        }

        // todo AccessToken 삭제
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            jwtRepository.removeJwtAuthRedisByAccessToken(accessToken);
        }

        return jwtUtil.createLogoutCookie();
    }

    public Authentication getAuthentication(String validAccessToken) {
        String username = jwtUtil.getUsername(validAccessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


}

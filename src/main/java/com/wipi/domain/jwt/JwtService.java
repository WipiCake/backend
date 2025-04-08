package com.wipi.domain.jwt;

import com.wipi.inferfaces.dto.ResIssueJwtDto;
import com.wipi.infra.jwt.JwtUtil;
import com.wipi.support.util.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResIssueJwtDto issueJwtAuth(Authentication authResult) {
        String username = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority();
        String access = jwtUtil.createAccessToken(username, role);
        String refresh = jwtUtil.createRefreshToken(username, role);

        JwtAuthRedis savedJwt = new JwtAuthRedis();
            savedJwt.setId("JWT:" + UUID.randomUUID());
            savedJwt.setAccessToken(access);
            savedJwt.setRefreshToken(refresh);
            savedJwt.setEmail(username);
            savedJwt.setAccessExpiration(jwtUtil.getExpirationFromToken(access));
            savedJwt.setRefreshExpiration(jwtUtil.getExpirationFromToken(refresh));
            savedJwt.setCreateAt(LocalDateTime.now());
            savedJwt.setUpdateAt(null);

        JwtAuthRedis jwtAuth = jwtRepository.saveJwtAuth(savedJwt);

        Cookie cookie = jwtUtil.createRefreshCookie(refresh);
        log.info("save jwtAuth issue : {}", Utils.toJson(jwtAuth));

        return new ResIssueJwtDto(
                jwtAuth.getAccessToken(),
                jwtAuth.getRefreshToken(),
                cookie
        );

    }

    public String reissueAccessByRefresh(HttpServletRequest request) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
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
        jwtRepository.saveJwtAuth(jwtAuth);
        log.info("accessUpdate : {}", reissueAccessToken);

        return reissueAccessToken;
    }

    public Authentication getAuthentication(String validAccessToken) {
        String username = jwtUtil.getUsername(validAccessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

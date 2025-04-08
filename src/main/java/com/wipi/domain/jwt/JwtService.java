package com.wipi.domain.jwt;

import com.wipi.inferfaces.dto.ResIssueJwtAuthDto;
import com.wipi.inferfaces.dto.ResReIssueJwtAuthDto;
import com.wipi.infra.jwt.JwtUtil;
import jakarta.persistence.Id;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtUtil jwtUtil;
    private final JwtRepository jwtRepository;
    private final UserDetailsService userDetailsService;

    public String ValidAccess(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            log.warn("accessToken header is missing or malformed");
            return null;
        }

        accessToken = accessToken.substring(7);

        JwtAuthRedis jwtAuth = jwtRepository.findByAccessToken(accessToken).orElse(null);
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
    public ResIssueJwtAuthDto issueJwtAuth(Authentication authResult) {
        String username = authResult.getName();
        String role = authResult.getAuthorities().iterator().next().getAuthority();
        String access = jwtUtil.createAccessToken(username, role);
        String refresh = jwtUtil.createRefreshToken(username, role);

        JwtAuthRedis jwtAuthRedis = jwtRepository.saveJwtAuth(new JwtAuthRedis(
                UUID.randomUUID().toString(),
                access,
                username,
                refresh,
                jwtUtil.getExpirationFromToken(access),
                jwtUtil.getExpirationFromToken(refresh),
                LocalDateTime.now(),
                null
        ));

        Cookie cookie = jwtUtil.createRefreshCookie(refresh);

        return new ResIssueJwtAuthDto(
                jwtAuthRedis.getAccessToken(),
                jwtAuthRedis.getRefreshToken(),
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

        if (refreshToken == null) {
            log.warn("refresh token not found in header");
            return null;
        }

        JwtAuthRedis jwtAuth = jwtRepository.findByRefreshToken(refreshToken).orElse(null);

        if (jwtAuth == null) {
            log.warn("refresh token not found in redis");
            return null;
        }

        if(!refreshToken.equals(jwtAuth.getRefreshToken())) {
            log.warn("refresh token does not match");
            return null;
        }

        if(LocalDateTime.now().isBefore(jwtAuth.getRefreshExpiration())){
            log.warn("refresh expired");
            return null;
        }

        String reissueAccessToken = jwtUtil.createAccessToken(
                jwtUtil.getUsername(jwtAuth.getAccessToken()),
                jwtUtil.getRole(jwtAuth.getAccessToken())
        );

        jwtAuth.setAccessToken(reissueAccessToken);
        jwtRepository.saveJwtAuth(jwtAuth);

        return reissueAccessToken;
    }

    public Authentication getAuthentication(String validAccessToken) {
        String username = jwtUtil.getUsername(validAccessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

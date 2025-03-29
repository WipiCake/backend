package com.wipi.filter;

import com.wipi.model.entity.RedisJwtEntity;
import com.wipi.service.front.JwtFrontService;
import com.wipi.util.FilterUtil;
import com.wipi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class SecurityJwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final FilterUtil filterUtil;
    private final JwtFrontService jwtFrontService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getHeader("Authorization") == null){
            filterChain.doFilter(request,response);
            return;
        }
        RedisJwtEntity authResult =
                jwtFrontService.JwtValidationByAccessToken(request.getHeader("Authorization"));

        if(authResult == null){
            log.warn("인증되지 않은 사용자");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String accessToken = authResult.getAccessToken();

        try {
            jwtUtil.isExpired(accessToken);
        }catch (ExpiredJwtException e){

            log.warn("access token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        jwtUtil.isAccessCategory(jwtUtil.getCategory(accessToken));

        filterUtil.setContextHodler(
           jwtUtil.getUsername(accessToken), jwtUtil.getRole(accessToken)
        );

        filterChain.doFilter(request,response);
    }
}

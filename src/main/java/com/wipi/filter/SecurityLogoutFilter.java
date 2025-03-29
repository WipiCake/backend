package com.wipi.filter;

import com.wipi.service.front.JwtFrontService;
import com.wipi.util.FilterUtil;
import com.wipi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class SecurityLogoutFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;
    private final JwtFrontService jwtFrontService;
    private final FilterUtil filterUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(!filterUtil.chkLogoutUrlAndMethod(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = filterUtil.chkIsRefresh(request);

        if(refresh == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        try{
            jwtUtil.isExpired(refresh);
        }catch (ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String category = jwtUtil.getCategory(refresh);
        if(!category.equals("refresh")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if(jwtFrontService.findByRefreshToken(refresh) == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Cookie cookie = jwtFrontService.deleteAuth(refresh);

        log.info("로그아웃");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

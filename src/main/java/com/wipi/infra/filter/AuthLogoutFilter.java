package com.wipi.infra.filter;

import com.wipi.domain.jwt.JwtService;
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
public class AuthLogoutFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if(!request.getRequestURI().equals("/logout") || !"POST".equals(request.getMethod()){
            filterChain.doFilter(request, response);
            return;
        }

        Cookie cookie = jwtService.logoutAndCreateExpiredCookie(request);

        log.info("로그아웃");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

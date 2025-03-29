package com.wipi.controller;

import com.wipi.exception.UserRuntimeException;
import com.wipi.model.entity.AuthJwtEntity;
import com.wipi.model.result.RestResult;
import com.wipi.service.front.JwtFrontService;
import com.wipi.util.FilterUtil;
import com.wipi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtUtil;
    private final JwtFrontService jwtFrontService;
    private final FilterUtil filterUtil;

    @PostMapping("/reissue")
    public RestResult accessTokenRessiue(HttpServletRequest request, HttpServletResponse response){
        String refresh = filterUtil.chkIsRefresh(request);

        if(refresh == null){
            throw new UserRuntimeException("refresh 토큰이 존재하지 않습니다");
        }

        try {
            jwtUtil.isExpired(refresh);
        }catch (ExpiredJwtException e){
            throw  new UserRuntimeException("토큰이 만료되었습니다");
        }

        if(!jwtUtil.getCategory(refresh).equals("refresh")){
            throw new UserRuntimeException("refresh 토큰이 존재하지 않습니다");
        }

        AuthJwtEntity resultAuth = jwtFrontService.reissueJwtToken(refresh);

        response.setHeader("access",resultAuth.getAccessToken());
        response.addCookie(jwtUtil.createCookie(resultAuth.getRefreshToken()));
        return new RestResult("access token reissue success","success");
    }


}

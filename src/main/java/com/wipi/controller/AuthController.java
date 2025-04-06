package com.wipi.controller;

import com.wipi.exception.UserRuntimeException;
import com.wipi.model.entity.AuthJwtEntity;
import com.wipi.model.result.RestResult;
import com.wipi.rest.RestUserSignup;
import com.wipi.service.front.JwtFrontService;
import com.wipi.util.FilterUtil;
import com.wipi.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(description = "인가관련 API", name = "인가 API")
@RequestMapping("/access")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final JwtFrontService jwtFrontService;
    private final FilterUtil filterUtil;


    @PostMapping("/reissue")
    @Operation(summary = "AccessToekn 재발급",
               description = "AccessToken이 만료된상황에서 RefreshToken(쿠키)가 유효할때 AccessToken을 재밥급 받습니다."  )
    @ApiResponse(
            responseCode = "200",
            description = "성공시 따로 응답메세지는 존재하지 않고, header에 AccessToken이 저장됩니다."
    )
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

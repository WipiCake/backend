package com.wipi.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipi.model.details.CustomUserDetails;
import com.wipi.model.entity.UserEntity;
import com.wipi.model.result.JwtTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilterUtil {

    private final ObjectMapper objectMapper;

    public void setContextHodler(String username, String role){
        log.info("set : {}, role : {}", username, role);

        UserEntity userEntity = new UserEntity(
                username,
                role
        );

        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                "",
                customUserDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Boolean chkLogoutUrlAndMethod(HttpServletRequest request){
        return request.getRequestURI().equals("/logout") && "POST".equals(request.getMethod());
    }

    public String chkIsRefresh(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                return cookie.getValue();
            }
        }
        return null;
    }

     public String setResponseMessage(HttpServletResponse response, String message,String username) throws JsonProcessingException {
         response.setContentType("application/json");
         response.setCharacterEncoding("UTF-8");

         Map<String, Object> responseBody = new HashMap<>();
         responseBody.put("message", "로그인에 성공했습니다.");
         responseBody.put("username", username);

         return objectMapper.writeValueAsString(responseBody);
     }



}

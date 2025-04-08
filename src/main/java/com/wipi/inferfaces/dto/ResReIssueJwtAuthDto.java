package com.wipi.inferfaces.dto;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResReIssueJwtAuthDto {
    private String accessToken;
    private String refreshToken;
    private Cookie cookie;
}

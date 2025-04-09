package com.wipi.inferfaces.model.dto.res;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResReissueJwtDto {
    private String accessToken;
    private String refreshToken;
    private Cookie cookie;
}

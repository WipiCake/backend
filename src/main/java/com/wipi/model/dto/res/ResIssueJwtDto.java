package com.wipi.model.dto.res;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResIssueJwtDto {
    private String accessToken;
    private String refreshToken;
    private ResponseCookie cookie;
}

package com.wipi.inferfaces.model.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResUserSignupDto {
    @Schema(description = "유저 아이디", example = "admin12")
    private String userId;

    @Schema(description = "이메일", example = "wipi@naver.com")
    private String email;

    @Schema(description = "권한", example = "ROLE_USER")
    private String role;
}

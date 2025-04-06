package com.wipi.model.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResUserSignupDto {
    @Schema(description = "아이디", example = "wipi1234")
    private String userId;

    @Schema(description = "이메일", example = "wipi@naver.com")
    private String email;
}

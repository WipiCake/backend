package com.wipi.rest;

import com.wipi.model.result.ResUserSignupDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestUserSignup {
    @Schema(description = "응답 코드", example = "200")
    private String status;

    @Schema(description = "응답 메시지", example = "회원가입에 성공하였습니다.")
    private String message;

    @Schema(description = "데이터")
    private ResUserSignupDto data;
}

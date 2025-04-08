package com.wipi.support.swagger.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(description = "로그인 API", name = "로그인 API")
public class LoginAPI {
    @Operation(
            summary = "로그인",
            description = "form-data: \n username:admin12@naver.com\n password:12345678"
                    + "성공 시, 응답 헤더에 access: accessToken 및 Cookie가 저장됩니다.\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    headers = {
                            @Header(
                                    name = "access",
                                    description = "access 토큰이 header access에발급",
                                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiIsInR...")
                            ),
                            @Header(
                                    name = "refresh",
                                    description = "refresh쿠키가 등록되고, refresh header에 발급 ",
                                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiIsInR...")
                            )
                    }
            ),
            @ApiResponse(responseCode = "401", description = "로그인 실패 - 아이디 또는 비밀번호 오류")
    })
    @PostMapping("auth/login")
    public void login(@RequestBody LoginRequest request) {
        // 실제 동작은 Spring Security Filter (ex: UsernamePasswordAuthenticationFilter)에서 처리됨
        throw new UnsupportedOperationException("이 API는 문서 전용이며 실제 동작하지 않습니다.");
    }

    @Schema(name = "LoginRequest", description = "로그인 요청 DTO")
    static class LoginRequest {
        @Schema(description = "사용자 아이디 또는 이메일", example = "jaemin123", requiredMode = Schema.RequiredMode.REQUIRED)
        public String username;

        @Schema(description = "사용자 비밀번호", example = "password123!", requiredMode = Schema.RequiredMode.REQUIRED)
        public String password;
    }
}

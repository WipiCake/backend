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
            description = """
        `form-data` 방식으로 로그인 요청을 보냅니다.  
        - `username`: admin12@naver.com  
        - `password`: 12345678  

        로그인에 성공하면 다음과 같은 토큰이 발급됩니다:  
        - 응답 헤더 `access`에 **Access Token** 포함  
        - `Refresh Token`은 **Cookie에 저장**되며, 응답 헤더 `refresh`에도 함께 발급됩니다.
        """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    headers = {
                            @Header(
                                    name = "access",
                                    description = "Access Token이 담긴 헤더",
                                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiIsInR...")
                            ),
                            @Header(
                                    name = "refresh",
                                    description = "Refresh Token이 쿠키에 저장되며, 동시에 헤더에도 포함됩니다.",
                                    schema = @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiIsInR...")
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인 실패 - 아이디 또는 비밀번호가 일치하지 않음"
            )
    })
    @PostMapping("/login")
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

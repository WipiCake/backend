package com.wipi.inferfaces.controller.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Tag(name = "Login", description = "로그인 관련 API")
public class LoginFilterApi {

    /**
     * Swagger 전용 Stub API입니다.
     * Spring Security의 로그인 Filter에서 실제 처리가 이루어지며,
     * 이 메서드는 문서화 용도로만 존재합니다.
     */
    @Operation(
            summary = "로그인 API (Swagger Stub)",
            description = "실제 인증은 Spring Security Filter에서 처리됩니다.\n\n" +
                    "Swagger에서 로그인 요청 형식을 보기 위한 Stub API입니다.\n\n" +
                    "요청 성공 시 accessToken은 Header(Authorization)에, refreshToken은 Cookie로 발급됩니다."
    )
    @PostMapping
    public void login(@RequestBody LoginApiRequest request) {
        // Swagger 문서 노출용. 실제 인증 로직은 Filter 처리
    }

}

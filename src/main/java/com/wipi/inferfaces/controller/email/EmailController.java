package com.wipi.inferfaces.controller.email;

import com.wipi.app.mail.EmailFrontService;
import com.wipi.model.rest.APIResponse;
import com.wipi.model.param.ProcessEmailVerificationParam;
import com.wipi.model.param.VerifyFindIdByEmailParam;
import com.wipi.model.param.VerifyRestPwByEmailParam;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

// RestResponseEntity 등 SpringDoc이 자동 해석할 수 있는 경우 어노테이션만 명시
// APIResponse<T> 같은 일반 제네릭 클래스 -> @Content, @Schema 로 반환모델 명시

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Email", description = "이메일 인증 관련 API")
public class EmailController {

    private final EmailFrontService emailFrontService;
    private final JwtProperties jwtProperties;
    
    @Operation(summary="이메일 인증 코드 발급", description="이메일 주소와 용도를 받아 인증코드를 발급")
    @ApiResponse(
            responseCode = "200",
            description = "인증코드 발급 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = APIResponse.class)
            )
    )
    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@RequestBody ProcessEmailVerificationParam param) {
        emailFrontService.processEmailVerification(param);
        return APIResponse.success("인증코드 발급에 성공하였습니다.");
    }

    @PostMapping("/verify/reset-pw")
    @Operation(summary = "비밀번호 재설정", description = "이메일 인증 코드를 확인하고 jwt 토큰 발급")
    @ApiResponse(
            responseCode = "200",
            description = "인증에 성공하였습니다.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = APIResponse.class)
            )
    )
    public APIResponse<String> verifyEmailVerificationCode(@RequestBody VerifyRestPwByEmailParam param, HttpServletResponse response) {
        Map<String,Object> data =  emailFrontService.verifyResetPw(param);
        log.info("data : {}", Utils.toJson(data));

        response.setHeader(jwtProperties.getAccessHeaderName(),"Bearer " + data.get("accessToken"));
        response.addCookie((Cookie) data.get("refreshCookie"));
        return APIResponse.success("인증에 성공하였습니다.");
    }


    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@RequestBody VerifyFindIdByEmailParam param) {
        String userId = emailFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }

}

package com.wipi.inferfaces.api.controller.sms;

import com.wipi.app.sms.SmsCoolFrontService;
import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.param.VerifyFindIdBySmsCoolParam;
import com.wipi.inferfaces.model.param.VerifyResetPwByCoolSmsParam;
import com.wipi.inferfaces.model.param.ProcessSendSmsCoolParam;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
@Tag(name = "SMS", description = "SMS 인증 관련 API")
@Slf4j
public class SmsController {

    private final SmsCoolFrontService smsCoolFrontService;
    private final JwtProperties jwtProperties;
    @Operation(
            summary = "sms 인증코드 발송",
            description = "휴대폰 번호로 sms 인증 코드 발송"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전송 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            )
    })
    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return APIResponse.success("SMS 전송 성공");
    }
    @Operation(
            summary = "SMS 인증 후 비밀번호 재설정 토큰 발급",
            description = "인증코드 검증 후 재설정용 JWT 토큰을 헤더·쿠키에 셋팅합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            )
    })
    @PostMapping("/verify/reset-pw")
    public APIResponse<String> verifyRestPw(@Valid@RequestBody VerifyResetPwByCoolSmsParam param, HttpServletResponse response) {
        Map<String,Object> data = smsCoolFrontService.verifyResetPw(param);
        log.info("data : {}", Utils.toJson(data));
        // 응답 헤더 / 쿠키에 jwt 설정
        response.setHeader(jwtProperties.getAccessHeaderName(),"Bearer " + data.get("accessToken"));
        response.addCookie((Cookie) data.get("refreshCookie"));
        return APIResponse.success("인증이 완료되었습니다.");
    }
    @Operation(
            summary = "SMS 인증 후 아이디 찾기",
            description = "인증코드 검증 후, 해당 휴대폰 번호에 연관된 사용자 아이디를 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = APIResponse.class)
                    )
            )
    })
    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@Valid @RequestBody VerifyFindIdBySmsCoolParam param) {
        String userId = smsCoolFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }
}

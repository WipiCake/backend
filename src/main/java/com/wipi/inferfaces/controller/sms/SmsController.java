package com.wipi.inferfaces.controller.sms;

import com.wipi.app.sms.SmsCoolFrontService;
import com.wipi.model.param.VerifyAuthBySmsCoolParam;
import com.wipi.model.rest.APIResponse;
import com.wipi.model.param.VerifyFindIdBySmsCoolParam;
import com.wipi.model.param.VerifyResetPwByCoolSmsParam;
import com.wipi.model.param.ProcessSendSmsCoolParam;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "SMS 인증 코드 발급", description = "purpose -> 아이디 찾기 : FIND-ID, 비밀번호 찾기 : FIND-PW, 회원가입 인증 : AUTH")
    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return APIResponse.success("SMS 전송 성공");
    }

    @Operation(summary = "비밀번호 변경 검증", description = "purpose : FIND-PW, " +
            "인증코드가 검증이 완료되면 JWT 토큰을 Header에 발급하고, " +
            "user//updatePw API를 요청하여 비밀번호를 변경합니다. ")
    @PostMapping("/verify/reset-pw")
    public APIResponse<String> verifyRestPw(@Valid@RequestBody VerifyResetPwByCoolSmsParam param, HttpServletResponse response) {
        Map<String,Object> data = smsCoolFrontService.verifyResetPw(param);
        log.info("data : {}", Utils.toJson(data));

        response.setHeader(jwtProperties.getAccessHeaderName(),"Bearer " + data.get("accessToken"));
        response.addCookie((Cookie) data.get("refreshCookie"));
        return APIResponse.success("인증이 완료되었습니다.");
    }

    @Operation(summary = "아이디 찾기 검증", description = "purpose : FIND-ID")
    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@Valid @RequestBody VerifyFindIdBySmsCoolParam param) {
        String userId = smsCoolFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }

    @Operation(summary = "회원가입 검증", description = "purpose : AUTH")
    @PostMapping("/verify/auth")
    public APIResponse<Void> auth(@Valid @RequestBody VerifyAuthBySmsCoolParam param) {
        smsCoolFrontService.verifyAuth(param);
        return APIResponse.success();
    }
}

package com.wipi.inferfaces.controller.sms;

import com.wipi.app.sms.SmsCoolFrontService;
import com.wipi.model.APIResponse;
import com.wipi.model.param.VerifyFindIdBySmsCoolParam;
import com.wipi.model.param.VerifyResetPwByCoolSmsParam;
import com.wipi.model.param.ProcessSendSmsCoolParam;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
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

    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return APIResponse.success("SMS 전송 성공");
    }

    @PostMapping("/verify/reset-pw")
    public APIResponse<String> verifyRestPw(@Valid@RequestBody VerifyResetPwByCoolSmsParam param, HttpServletResponse response) {
        Map<String,Object> data = smsCoolFrontService.verifyResetPw(param);
        log.info("data : {}", Utils.toJson(data));

        response.setHeader(jwtProperties.getAccessHeaderName(),"Bearer " + data.get("accessToken"));
        response.addCookie((Cookie) data.get("refreshCookie"));
        return APIResponse.success("인증이 완료되었습니다.");
    }

    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@Valid @RequestBody VerifyFindIdBySmsCoolParam param) {
        String userId = smsCoolFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }
}

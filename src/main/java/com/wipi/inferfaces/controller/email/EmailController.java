package com.wipi.inferfaces.controller.email;

import com.wipi.app.mail.EmailFrontService;
import com.wipi.model.rest.APIResponse;
import com.wipi.model.param.ProcessEmailVerificationParam;
import com.wipi.model.param.VerifyFindIdByEmailParam;
import com.wipi.model.param.VerifyRestPwByEmailParam;
import com.wipi.support.properties.JwtProperties;
import com.wipi.support.util.Utils;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Email", description = "이메일 인증 관련 API")
public class EmailController {

    private final EmailFrontService emailFrontService;
    private final JwtProperties jwtProperties;

    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@RequestBody ProcessEmailVerificationParam param) {
        emailFrontService.processEmailVerification(param);
        return APIResponse.success("인증코드 발급에 성공하였습니다.");
    }

    @PostMapping("/verify/reset-pw")
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

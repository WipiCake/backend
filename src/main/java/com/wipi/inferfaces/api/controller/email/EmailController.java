package com.wipi.inferfaces.api.controller.email;

import com.wipi.app.mail.EmailFrontService;
import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.param.ProcessEmailVerificationParam;
import com.wipi.inferfaces.model.param.VerifyFindIdByEmailParam;
import com.wipi.inferfaces.model.param.VerifyRestPwByEmailParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Email", description = "이메일 인증 관련 API")
public class EmailController {

    private final EmailFrontService emailFrontService;

    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@RequestBody ProcessEmailVerificationParam param) {
        emailFrontService.processEmailVerification(param);
        return APIResponse.success("인증코드 발급에 성공하였습니다.");
    }

    @PostMapping("/verify/reset-pw")
    public APIResponse<String> verifyEmailVerificationCode(@RequestBody VerifyRestPwByEmailParam param) {
        emailFrontService.verifyResetPw(param);
        return APIResponse.success("인증에 성공하였습니다.");
    }


    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@RequestBody VerifyFindIdByEmailParam param) {
        String userId = emailFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }

}

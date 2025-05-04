package com.wipi.inferfaces.api.controller.sms;

import com.wipi.app.sms.SmsCoolFrontService;
import com.wipi.inferfaces.model.APIResponse;
import com.wipi.inferfaces.model.param.VerifyFindIdBySmsCoolParam;
import com.wipi.inferfaces.model.param.VerifyResetPwByCoolSmsParam;
import com.wipi.inferfaces.model.param.ProcessSendSmsCoolParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
@Tag(name = "SMS", description = "SMS 인증 관련 API")
public class SmsController {

    private final SmsCoolFrontService smsCoolFrontService;

    @PostMapping("/code/issue")
    public APIResponse<String> issueEmailVerificationCode(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return APIResponse.success("SMS 전송 성공");
    }

    @PostMapping("/verify/reset-pw")
    public APIResponse<String> verifyRestPw(@Valid@RequestBody VerifyResetPwByCoolSmsParam param) {
        smsCoolFrontService.verifyResetPw(param);
        return APIResponse.success("인증이 완료되었습니다.");
    }

    @PostMapping("/verify/find-id")
    public APIResponse<String> verifyFindId(@Valid @RequestBody VerifyFindIdBySmsCoolParam param) {
        String userId = smsCoolFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }
}

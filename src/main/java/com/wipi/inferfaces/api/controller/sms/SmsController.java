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
    @Operation(summary = "SMS 인증코드 발송", description = "휴대폰 번호로 SMS 인증코드를 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SMS 전송 성공",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse<String> issueEmailVerificationCode(@Valid @RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return APIResponse.success("SMS 전송 성공");
    }

    @PostMapping("/verify/reset-pw")
    @Operation(summary = "비밀번호 재설정용 SMS 인증코드 검증", description = "SMS 인증코드를 검증하고, 인증 완료 시 JWT를 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 재설정 인증 성공",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse<String> verifyRestPw(@Valid@RequestBody VerifyResetPwByCoolSmsParam param) {
        smsCoolFrontService.verifyResetPw(param);
        return APIResponse.success("인증이 완료되었습니다.");
    }

    @PostMapping("/verify/find-id")
    @Operation(summary = "아이디 찾기용 SMS 인증코드 검증", description = "SMS 인증코드를 검증하여 가입된 사용자의 아이디를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "아이디 조회 성공",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "인증 실패",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    public APIResponse<String> verifyFindId(@Valid @RequestBody VerifyFindIdBySmsCoolParam param) {
        String userId = smsCoolFrontService.verifyFindId(param);
        return APIResponse.success(userId);
    }
}

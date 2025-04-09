package com.wipi.inferfaces.api.controller;

import com.wipi.app.EmailFrontService;
import com.wipi.inferfaces.model.param.ProcessEmailVerification;
import com.wipi.inferfaces.model.rest.RestMessage;
import com.wipi.inferfaces.model.rest.RestResponse;
import com.wipi.inferfaces.model.rest.RestResponseEntity;
import com.wipi.support.swagger.wrapper.RestProcessEmailVerificationWrapper;
import com.wipi.support.swagger.wrapper.RestProcessIssueTempPasswordWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/verify")
    @Operation(summary = "이메일 인증코드 발급", description = "입력된 이메일로 인증코드를 발급하고 메일을 전송합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공 응답 [status,message] ",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestProcessEmailVerificationWrapper.class)
            )
    )
    public ResponseEntity<RestMessage> processEmailVerification(@RequestBody ProcessEmailVerification param) {
        emailFrontService.processEmailVerification(param);
        return ResponseEntity.ok(new RestMessage("이메일 인증코드가 발급되었습니다."));
    }

    @PostMapping("/tempPw")
    @Operation(summary = "임시 비밀번호 발급", description = "입력된 이메일로 임시비밀번호를 전송합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공 응답 [status,message] ",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RestProcessIssueTempPasswordWrapper.class)
            )
    )
    public ResponseEntity<RestResponse<Void>> processIssueTempPassword(@RequestBody ProcessEmailVerification param) {
        emailFrontService.processEmailVerification(param);
        return RestResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
    }

}

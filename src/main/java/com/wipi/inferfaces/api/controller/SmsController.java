package com.wipi.inferfaces.api.controller;


import com.wipi.app.SmsCoolFrontService;
import com.wipi.inferfaces.model.dto.req.VerifySmsCoolVerificationCodeParam;
import com.wipi.inferfaces.model.param.ProcessSendSmsCoolParam;
import com.wipi.inferfaces.model.rest.RestMessage;
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
@RequestMapping("/sms")
@Tag(name = "SMS", description = "SMS 인증 관련 API")
public class SmsController {

    private final SmsCoolFrontService smsCoolFrontService;

    @PostMapping("/code/issue")
    @Operation(
            summary = "sms 인증코드 최초 발급",
            description = "입력된 전화번호로 인증코드를 최초 발급하고 SMS를 전송합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "SMS 전송 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestMessage.class))
    )
    public ResponseEntity<RestMessage> issueEmailVerificationCode(@RequestBody ProcessSendSmsCoolParam param) {
        smsCoolFrontService.sendSmsCoolProcess(param);
        return ResponseEntity.ok(new RestMessage("SMS 전송 성공"));
    }

    @PostMapping("/code/verify")
    @Operation(
            summary = "sms 인증코드 검증",
            description = "인증이 완료되었습니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "인증 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestMessage.class))
    )
    public ResponseEntity<RestMessage> verifySmsCoolVerificationCode(@RequestBody VerifySmsCoolVerificationCodeParam param) {
        smsCoolFrontService.verifySmsCoolVerificationCode(param);
        return ResponseEntity.ok(new RestMessage("인증이 완료되었습니다."));
    }



}

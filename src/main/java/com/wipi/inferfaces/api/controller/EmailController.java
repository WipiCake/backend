package com.wipi.inferfaces.api.controller;

import com.wipi.app.EmailFrontService;
import com.wipi.inferfaces.model.dto.req.ReissueEmailVerificationCodeParam;
import com.wipi.inferfaces.model.param.ProcessEmailVerificationParam;
import com.wipi.inferfaces.model.param.ProcessIssueTempPasswordParam;
import com.wipi.inferfaces.model.param.VerifyEmailVerificationCodeParam;
import com.wipi.inferfaces.model.rest.RestMessage;
import com.wipi.inferfaces.model.rest.RestResponse;
import com.wipi.inferfaces.model.rest.RestResponseEntity;
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

    @PostMapping("/code/issue")
    @Operation(
            summary = "이메일 인증코드 최초 발급",
            description = "입력된 이메일로 인증코드를 최초 발급하고 메일을 전송합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "인증코드 발급 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestMessage.class))
    )
    public ResponseEntity<RestMessage> issueEmailVerificationCode(@RequestBody ProcessEmailVerificationParam param) {
        emailFrontService.processEmailVerification(param);
        return ResponseEntity.ok(new RestMessage("이메일 인증코드가 발급되었습니다."));
    }

    @PostMapping("/code/reissue")
    @Operation(
            summary = "이메일 인증코드 재발급",
            description = "이전에 발급된 인증코드를 제거하고 새로 발급합니다. 2분 제한 적용됨."
    )
    @ApiResponse(
            responseCode = "200",
            description = "인증코드 재발급 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestMessage.class))
    )
    public ResponseEntity<RestMessage> reissueEmailVerificationCode(@RequestBody ReissueEmailVerificationCodeParam param) {
        emailFrontService.resReissueEmailVerificationCode(param);
        return ResponseEntity.ok(new RestMessage("이메일 인증코드가 재발급되었습니다."));
    }

    @PostMapping("/temp-password/issue")
    @Operation(
            summary = "임시 비밀번호 발급",
            description = "해당 이메일로 임시 비밀번호를 전송합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "임시 비밀번호 발급 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    )
    public ResponseEntity<RestResponse<Void>> issueTempPassword(@RequestBody ProcessIssueTempPasswordParam param) {
        emailFrontService.processIssueTempPassword(param);
        return RestResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
    }

    @PostMapping("/code/verify")
    @Operation(
            summary = "이메일 인증 코드 검증",
            description = "입력된 인증코드가 이메일에 발급된 코드와 일치하는지 검증합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "인증 성공 시 이메일 주소 반환",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestResponse.class))
    )
    public ResponseEntity<RestResponse<Void>> verifyEmailVerificationCode(@RequestBody VerifyEmailVerificationCodeParam param) {
        emailFrontService.verifyEmailVerificationCode(param);
        return RestResponseEntity.ok(param.getFromEmail() + " : 인증되었습니다.");
    }


}

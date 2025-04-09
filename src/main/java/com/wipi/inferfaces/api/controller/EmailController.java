package com.wipi.inferfaces.api.controller;

import com.wipi.app.EmailFrontService;
import com.wipi.inferfaces.model.param.ProcessEmailVerification;
import com.wipi.inferfaces.model.rest.RestMessage;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "이메일 인증코드 발급", description = "입력된 이메일로 인증코드를 발급하고 메일을 전송합니다.")
    @PostMapping("/verify")
    public ResponseEntity<RestMessage> processEmailVerification(@RequestBody ProcessEmailVerification param) {
        emailFrontService.processEmailVerification(param);
        return ResponseEntity.ok(new RestMessage("이메일 인증코드가 성공적으로 발급되었습니다."));
    }


}

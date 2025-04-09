package com.wipi.support.swagger.wrapper;

import com.wipi.inferfaces.model.rest.RestResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public class RestProcessEmailVerificationWrapper extends RestResponse<Void> {
    @Schema(example = "200", description = "응답 메시지")
    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Schema(example = "이메일 인증코드가 발급되었습니다.", description = "응답 메시지")
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RestProcessEmailVerificationWrapper(int status, String message) {
        super(status, message);
    }
}

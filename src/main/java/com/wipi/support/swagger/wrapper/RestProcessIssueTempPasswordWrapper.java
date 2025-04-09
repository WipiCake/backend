package com.wipi.support.swagger.wrapper;

import com.wipi.inferfaces.model.rest.RestResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "임시 비밀번호 이메일 전송 예시")
public class RestProcessIssueTempPasswordWrapper extends RestResponse<Void> {

    @Schema(example = "200", description = "응답 메시지")
    @Override
    public int getStatus() {
        return super.getStatus();
    }

    @Schema(example = "임시 비밀번호가 이메일로 전송되었습니다.", description = "응답 메시지")
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public RestProcessIssueTempPasswordWrapper(int status, String message) {
        super(status, message);
    }
}
